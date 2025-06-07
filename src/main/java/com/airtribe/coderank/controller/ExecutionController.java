package com.airtribe.coderank.controller;


import com.airtribe.coderank.dto.CodeExecutionRequest;
import com.airtribe.coderank.dto.CodeExecutionResponse;
import com.airtribe.coderank.dto.CodeSnippetDto;
import com.airtribe.coderank.entity.User;
import com.airtribe.coderank.service.ExecutionService;
import com.airtribe.coderank.service.kafka.CodeExecutionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ExecutionController {


    @Autowired
    private ExecutionService executionService;

    @Autowired
    private CodeExecutionProducer producer;


    /**
     *
     * @param executionId
     * @return
     */

    @GetMapping("/get-results/{executionId}")
    public ResponseEntity<?> getExecutionResult(@PathVariable String executionId){
        CodeExecutionResponse codeExecutionResponse = executionService.getExecutionResult(executionId);
        return ResponseEntity.ok(codeExecutionResponse);
    }


    /**
     *
     * @param codeSnippet
     * @return
     */

    @PostMapping("/api/execute")
    public String executeSpecificSnippet(@RequestBody CodeSnippetDto codeSnippet){
        return executionService.handleCodeSnippet(codeSnippet);
    }




}
