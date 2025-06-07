package com.airtribe.coderank.service.kafka;

import com.airtribe.coderank.dto.CodeExecutionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CodeExecutionProducer {


    @Autowired
    private KafkaTemplate<String, CodeExecutionRequest> kafkaTemplate;

    private final String topic = "code-execution";

    /**
     *
     * @param request
     * @param executionId
     * @param snippetFilePath
     * @param snippetFileName
     */
    public void sendCodeExecutionRequest( CodeExecutionRequest request,String executionId, String snippetFilePath, String snippetFileName ){
        kafkaTemplate.send(topic, executionId, request);
    }

}
