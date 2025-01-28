package com.app.timetable.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController implements ErrorController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request,Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", exception.getMessage());

        modelAndView.setViewName("error");
        return modelAndView;
    }

}
