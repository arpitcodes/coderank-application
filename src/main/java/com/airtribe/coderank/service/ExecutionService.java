package com.airtribe.coderank.service;

import com.airtribe.coderank.dto.CodeExecutionRequest;
import com.airtribe.coderank.dto.CodeExecutionResponse;
import com.airtribe.coderank.dto.CodeSnippetDto;
import com.airtribe.coderank.entity.CodeSnippet;
import com.airtribe.coderank.entity.User;
import com.airtribe.coderank.repository.CodeSnippetRepository;
import com.airtribe.coderank.repository.UserRepository;
import com.airtribe.coderank.service.kafka.CodeExecutionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class ExecutionService {

    private static String snippetFilePath = null;
    private static String snippetFileName = null;
    private static String executionId = null;
    @Autowired
    private CodeSnippetRepository codeSnippetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeExecutionProducer codeExecutionProducer;

    /**
     *
     * @param codeSnippetDto
     * @return
     */
    public String handleCodeSnippet(CodeSnippetDto codeSnippetDto){
        saveCodeSnippet(codeSnippetDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        CodeExecutionRequest codeExecutionRequest = new CodeExecutionRequest();
        codeExecutionRequest.setCode(codeSnippetDto.getCodeSnippet());
        codeExecutionRequest.setUserId(user.getId().toString());
        codeExecutionRequest.setLanguage(codeSnippetDto.getLanguageName());
        codeExecutionRequest.setExecutionId(executionId);
        codeExecutionRequest.setSnippetFilePath(snippetFilePath);
        codeExecutionRequest.setSnippetFileName(snippetFileName);

        codeExecutionProducer.sendCodeExecutionRequest(codeExecutionRequest, executionId, snippetFilePath, snippetFileName);

        return "Code Execution started with execution id: "+executionId;

    }

    /**
     *
     * @param codeSnippetDto
     */
    public void saveCodeSnippet(CodeSnippetDto codeSnippetDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        String username = currentUser.getEmail().toLowerCase();
        String languageName = codeSnippetDto.getLanguageName();
        String extension = getExtension(languageName);
        snippetFilePath = "C:\\tmp\\"+username+"\\"+languageName+"\\input_files\\";
        executionId = UUID.randomUUID().toString();
        snippetFileName = executionId+extension;
        saveCodeSnippetInFile(codeSnippetDto.getCodeSnippet(), snippetFilePath+snippetFileName);
        CodeSnippet codeSnippet = new CodeSnippet();
        codeSnippet.setUser(currentUser);
        codeSnippet.setExecutionId(executionId);
        codeSnippet.setSnippetFilePath(snippetFilePath+snippetFileName);
        codeSnippet.setLanguageName(codeSnippetDto.getLanguageName());
        codeSnippetRepository.save(codeSnippet);
    }

    private String getExtension(String languageName) {
        switch (languageName){
            case "python":
                return ".py";
            case "java":
                return ".java";
            case "c++":
                return ".cpp";
            case "javascript":
                return ".js";
            default:
                return "";
        }
    }

    public void saveCodeSnippetInFile(String code, String filePath){
        File file = new File(filePath);
        try{
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();       // create the file if it doesn't exist
                System.out.println("File created at: " + file.getAbsolutePath());
            } else {
                System.out.println("File already exists. Writing to it...");
            }
            FileWriter writer = new FileWriter(file, true); // set true for append mode
            writer.write(code);
            writer.close();
            System.out.println("Write successful.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CodeExecutionResponse getExecutionResult(String executionId) {
        CodeSnippet codeSnippet = codeSnippetRepository.findCodeSnippetByExecutionId(executionId);
        String content = null;
        String outputFilePath = codeSnippet.getOutputFilePath();
        try{
            Path path = Path.of(outputFilePath);
            content = Files.readString(path);
            System.out.println("File Content:\n"+content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CodeExecutionResponse codeExecutionResponse= new CodeExecutionResponse();
        codeExecutionResponse.setOutput(content);
        return codeExecutionResponse;
    }


}
