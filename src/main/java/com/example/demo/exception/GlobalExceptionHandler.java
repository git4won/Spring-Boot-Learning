package com.example.demo.exception;

import com.example.demo.domain.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


// 创建全局异常处理类
// 通过使用 @ControllerAdvice 定义统一的异常处理类，而不是在每个 Controller 中逐个定义。
// @ExceptionHandler 用来定义函数针对的异常类型。

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandle(HttpServletRequest req, Exception e)  throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");

        return mav;
    }

    // 只需在 @ExceptionHandler 之后加入 @ResponseBody，就能让处理函数 return 的内容转换为 JSON 格式。
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception {
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage((e.getMessage()));
        r.setCode(ErrorInfo.ERROR);
        r.setData("Some Data");
        r.setUrl(req.getRequestURL().toString());

        return r;
    }


}
