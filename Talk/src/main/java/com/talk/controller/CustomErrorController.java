package com.talk.controller;


import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Get error attributes from the request
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(
                new ServletWebRequest(request),
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE, ErrorAttributeOptions.Include.EXCEPTION)
        );

        // Set error attributes as model attributes
      model.addAllAttributes(errorAttributes);

        return "error";
    }

    // No @Override annotation here
    public String getErrorPath() {
        return "/error";
    }
}
