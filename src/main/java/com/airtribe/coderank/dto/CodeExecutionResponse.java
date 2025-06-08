package com.airtribe.coderank.dto;

public class CodeExecutionResponse {

    private String output;


    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public CodeExecutionResponse() {
    }

    public CodeExecutionResponse(String output) {
        this.output = output;
    }
}
