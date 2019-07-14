package com.berritus.mis.controller.study;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: 2019/7/14
 */
@Controller
@RequestMapping("/layui")
public class LayuiController {

    // http://localhost:8083/layui/sys/param/add
    @RequestMapping("/sys/param/add")
    public String sysParamAdd(Model model) {
        // model.addAttribute("message", "你好啊");
        return "study/layui/system_param_add";
    }
}
