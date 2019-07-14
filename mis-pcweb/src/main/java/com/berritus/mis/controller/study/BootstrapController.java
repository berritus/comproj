package com.berritus.mis.controller.study;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: 2019/7/13
 */
@Controller
@RequestMapping("/study")
public class BootstrapController {

    // http://localhost:8083/study/jsFunction
    @RequestMapping("/jsFunction")
    public String JsFunction(Model model) {
        model.addAttribute("message", "你好啊");
        return "study/jsFunction";
    }

    // http://localhost:8083/study/bootstrap/form
    @RequestMapping("/bootstrap/form")
    public String bootstrapForm(Model model) {
        model.addAttribute("message", "你好啊");
        return "study/bootstrap/form";
    }
}
