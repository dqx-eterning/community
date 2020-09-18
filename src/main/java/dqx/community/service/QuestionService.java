package dqx.community.service;

import dqx.community.dto.QuestionQueryDTO;
import dqx.community.dto.PaginationDTO;
import dqx.community.dto.QuestionDTO;
import dqx.community.enums.SortEnum;
import dqx.community.exception.CustomizeErrorCode;
import dqx.community.exception.CustomizeException;
import dqx.community.mapper.QuestionExtMapper;
import dqx.community.mapper.QuestionMapper;
import dqx.community.mapper.UserMapper;
import dqx.community.model.Question;
import dqx.community.model.QuestionExample;
import dqx.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    //通过页面传递的参数，进行查询分页
    public PaginationDTO findList(String search, Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .collect(Collectors.joining("|"));
        }
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount =  questionExtMapper.countBySearch(questionQueryDTO);


        if( totalCount % size == 0){
            totalPage= totalCount / size;
        }else {
            totalPage = (totalCount / size) + 1;
        }

        if (page<0){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);

        Integer offset = page < 1 ? 0 : size * (page - 1);
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> list = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : list) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            //把question中属性拷贝至questionDTO 由spring提供
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        //System.out.println(totalCount);
        return paginationDTO;
    }

    //我的问题页面分页展示
    public PaginationDTO findList(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        if( totalCount % size == 0){
            totalPage= totalCount / size;
        }else {
            totalPage = (totalCount / size) + 1;
        }
        if (page<0){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);
        Integer offset = size*(page - 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> list = questionMapper.selectByExampleWithBLOBsWithRowbounds(example,
                new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : list) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            //把question中属性拷贝至questionDTO 由spring提供
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        //System.out.println(totalCount);
        return paginationDTO;
    }

    //根据id查询问题。展示问题详情页面
    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    //根据判断用户是否创建者，更新或者创建问题
    public void creatOrUpdate(Question question) {
        if (question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }

    }

    //增加浏览数
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    //根据问题查询
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
