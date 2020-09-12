package dqx.community.controller;

import dqx.community.dto.PaginationDTO;
import dqx.community.dto.QuestionDTO;
import dqx.community.mapper.QuestionMapper;
import dqx.community.mapper.UserMapper;
import dqx.community.model.Question;
import dqx.community.model.User;
import dqx.community.service.QuestionService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,Model model,
                        @RequestParam(name= "page",defaultValue = "1") Integer page,
                        @RequestParam(name= "size",defaultValue = "5") Integer size){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        PaginationDTO pagination= questionService.findList(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
