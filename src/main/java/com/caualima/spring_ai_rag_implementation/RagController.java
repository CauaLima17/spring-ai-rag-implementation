package com.caualima.spring_ai_rag_implementation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rag")
public class RagController {

    @GetMapping("/hello-world")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello World! This is working :)");
    }
}
