package com.airtribe.coderank.dto;

public class CodeExecutionRequest {
    private String language;
    private String code;
    private String userId;
    private String executionId;
    private String snippetFilePath;
    private String snippetFileName;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public CodeExecutionRequest(String language, String code, String userId, String executionId, String snippetFilePath, String snippetFileName) {
        this.language = language;
        this.code = code;
        this.userId = userId;
        this.executionId = executionId;
        this.snippetFilePath = snippetFilePath;
        this.snippetFileName = snippetFileName;
    }

    public String getSnippetFilePath() {
        return snippetFilePath;
    }

    public void setSnippetFilePath(String snippetFilePath) {
        this.snippetFilePath = snippetFilePath;
    }

    public String getSnippetFileName() {
        return snippetFileName;
    }

    public void setSnippetFileName(String snippetFileName) {
        this.snippetFileName = snippetFileName;
    }

    public CodeExecutionRequest() {
    }
}
