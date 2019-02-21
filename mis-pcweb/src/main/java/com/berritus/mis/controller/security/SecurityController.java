package com.berritus.mis.controller.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {
//    @GetMapping("/")
//    public String root(){
//        return "redirect:/index";
//    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("message", "点击下面登录");
        return "index";
    }

    @GetMapping("/user/index")
    public String userIndex(){
        return "user/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/login")
    public String userLogin(Model model){
        model.addAttribute("message", "login sucess");
        return "success";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("message", "login error");
        return "login";
    }

    @GetMapping("/401")
    public String accessDenied(Model model){
        model.addAttribute("message", "登录失败");
        return "401";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/demo/hello")
    public String demoHello(Model model){
        model.addAttribute("message", "有权限");
        return "success";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET, value = "/demo/user")
    public String demoUser(Model model){
        model.addAttribute("message", "有权限");
        return "success";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/403")
    public String accessDenied403(Model model){
        model.addAttribute("message", "权限不足，请联系管理员");
        return "403";
    }
}
