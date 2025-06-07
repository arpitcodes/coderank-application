package com.airtribe.coderank.service.kafka;

import com.airtribe.coderank.dto.CodeExecutionRequest;
import com.airtribe.coderank.entity.CodeSnippet;
import com.airtribe.coderank.repository.CodeSnippetRepository;
import com.airtribe.coderank.repository.UserRepository;
import com.airtribe.coderank.util.ExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.*;



@Service
public class CodeExecutionConsumer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeSnippetRepository codeSnippetRepository;


    /**
     *
     * @param request
     * @throws IOException
     */

    @KafkaListener(topics = "code-execution", groupId="executor-group")
   public void consume(CodeExecutionRequest request) throws IOException {
        String OUTPUT_FILE_PATH = writeOutputFile(request);
        Long snippetId = codeSnippetRepository.findIdByExecutionId(request.getExecutionId());
        CodeSnippet codeSnippet = codeSnippetRepository.findById(snippetId).get();
        codeSnippet.setOutputFilePath(OUTPUT_FILE_PATH+request.getExecutionId()+".txt");
        codeSnippetRepository.save(codeSnippet);
        System.out.println("Execution Id: "+request.getExecutionId());
    }

    /**
     *
     * @param request
     * @return
     * @throws IOException
     */

    public String writeOutputFile(CodeExecutionRequest request) throws IOException {
        String imageName = ExecutionUtils.getImageNameByLanguage(request.getLanguage());
        String filePath = request.getSnippetFilePath();
        String fileName = request.getSnippetFileName();
        ProcessBuilder processBuilder=null;

        switch (request.getLanguage().toLowerCase()){
//            case "java":
//                processBuilder = new ProcessBuilder(
//                        "docker","run","--rm","-v",
//                        filePath+":/user/src/app",
//                        imageName,
//                        "\"javac "+fileName+".java && java "+fileName+"\""
//                );
//
//                break;
//            case "c++":
//                processBuilder = new ProcessBuilder(
//                        "docker", "run", "--rm",
//                        "-v", filePath+":/usr/src/app",
//                        imageName,
//                        "\"g++ "+fileName+".cpp -o "+fileName+" && ./"+fileName
//                );
//                break;
            case "javascript":
            case "python":
                processBuilder = new ProcessBuilder(
                        "docker", "run", "--rm",
                        "-v", filePath+":/usr/src/app",
                        imageName,
                        fileName
                );
                break;
            default:
                System.out.println("Language not found!!");
        }


        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );

        String outputText = "";
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            outputText += line+'\n';
        }

        String userName = userRepository.findById(Integer.parseInt(request.getUserId())).get().getUsername();

        String OUTPUT_FILE_PATH="C:\\tmp\\"+userName+"\\python\\output_files\\";


        File outputFile = new File(OUTPUT_FILE_PATH+request.getExecutionId()+".txt");
        try{
            outputFile.getParentFile().mkdirs();
            if (!outputFile.exists()) {
                outputFile.createNewFile();       // create the file if it doesn't exist
                System.out.println("File created at: " + outputFile.getAbsolutePath());
            } else {
                System.out.println("File already exists. Writing to it...");
            }
            FileWriter writer = new FileWriter(outputFile, true); // set true for append mode
            writer.write(outputText);
            writer.close();
            System.out.println("Write successful.");
            return OUTPUT_FILE_PATH;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
