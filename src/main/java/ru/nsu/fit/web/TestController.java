package ru.nsu.fit.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {
    @RequestMapping(path = "/test")
    ResponseEntity<?> test(){
        List<String> test = Arrays.asList("This", "is", "totally", "not", "a", "hello", "world");
        return ResponseEntity.ok(test);
    }
}
