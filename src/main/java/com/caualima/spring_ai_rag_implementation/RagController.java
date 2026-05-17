package com.caualima.spring_ai_rag_implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rag")
public class RagController {

    private final RagService ragService;

    @Autowired
    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/hello-world")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello World! This is working :)");
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String message) {
        String content = ragService.generateContent(message);
        return ResponseEntity.ok(content);
    }

    @PostMapping("/add-content")
    public ResponseEntity<Void> addContent(@RequestBody String content) {
        ragService.addNewContent(content);
        return ResponseEntity.ok().build();
    }
}
