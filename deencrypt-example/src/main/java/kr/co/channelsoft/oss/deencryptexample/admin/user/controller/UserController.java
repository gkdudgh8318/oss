package kr.co.channelsoft.oss.deencryptexample.admin.user.controller;

import java.util.Map;

import kr.co.channelsoft.oss.deencryptexample.admin.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    
    @Autowired
    private UserService service;
    
    /* 사용자 관리 Form */
    @RequestMapping("/form")
    public String form() {
    
        return "admin/user";
    }
    
    @RequestMapping(value ="/list", produces="text/plain; charset=UTF-8")
    @ResponseBody
    public String list(@RequestParam Map<String, String> params) throws Exception {
        
        return new ObjectMapper().writeValueAsString(service.getUserList(params));
    }
    
    @RequestMapping(value ="/save", produces="text/plain; charset=UTF-8")
    @ResponseBody
    public String save(@RequestParam Map<String, String> params) throws Exception {
    
        return new ObjectMapper().writeValueAsString(service.saveUser(params));
    }
    
    @RequestMapping(value ="/remove")
    @ResponseBody
    public String remove(@RequestParam Map<String, String> params) throws Exception {

        return new ObjectMapper().writeValueAsString(service.removeUser(params));
    }
    
}