package com.example.demo.error;

import java.net.URI;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
@RestController
public class ApiErrorController implements ErrorController {
    
    // @RequestMapping("/error")
    // public ProblemDetail handleError(HttpServletRequest request){
    //      int status = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    //     ProblemDetail problem = ProblemDetail.forStatus(status);
    //     problem.setTitle(HttpStatus.valueOf(status).getReasonPhrase());
    //     problem.setDetail("Endpoint does not exist or method not allowed");
    //     problem.setInstance(URI.create(request.getRequestURI()));

    //     return problem;
    // }
}