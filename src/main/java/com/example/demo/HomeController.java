package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

/**
     * “파일 확장자(.)가 없는 모든 경로”를 index.html 로 포워드
     * → React 라우터가 처리하게 한다.
     */
@RequestMapping({
        "/", 
        "/{path:[^\\.]+}", 
        "/**/{path:[^\\.]+}"
})
public String forward() {
        return "forward:/index.html";
}
}