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
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        if (page<0){
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

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
}
