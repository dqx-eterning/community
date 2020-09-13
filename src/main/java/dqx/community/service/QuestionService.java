package dqx.community.service;

import dqx.community.dto.PaginationDTO;
import dqx.community.dto.QuestionDTO;
import dqx.community.mapper.QuestionMapper;
import dqx.community.mapper.UserMapper;
import dqx.community.model.Question;
import dqx.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO findList(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        Integer totalCount = questionMapper.count();
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
        List<Question> list = questionMapper.findList(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : list) {
            User user = userMapper.findById(question.getCreator());
            //把question中属性拷贝至questionDTO 由spring提供
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        //System.out.println(totalCount);
        return paginationDTO;
    }

    public PaginationDTO findList(int userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);
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
        List<Question> list = questionMapper.findListByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : list) {
            User user = userMapper.findById(question.getCreator());
            //把question中属性拷贝至questionDTO 由spring提供
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        //System.out.println(totalCount);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        User user = userMapper.findById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void creatOrUpdate(Question question) {
        if (question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.creat(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }

    }
}
