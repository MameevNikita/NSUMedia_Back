package ru.nsu.fit.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.database.entities.TestEntity;
import ru.nsu.fit.database.repositories.TestRepository;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {
    private TestRepository testRepository;

    public TestController(TestRepository testRepository){
        this.testRepository = testRepository;
    }

    @RequestMapping(path = "/test")
    ResponseEntity<?> test(){
        testRepository.deleteAll();

        for(String word : Arrays.asList("This", "is", "totally", "not", "a", "hello", "world")){
            testRepository.save(new TestEntity(word));
        }

        return ResponseEntity.ok(testRepository.findAll());
    }
}
