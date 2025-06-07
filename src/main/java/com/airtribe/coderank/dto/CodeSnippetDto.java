package com.airtribe.coderank.dto;

public class CodeSnippetDto {

    private String languageName;
    private String codeSnippet;



    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }

    public void setCodeSnippet(String codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    public CodeSnippetDto() {
    }

    public CodeSnippetDto(int userId, Long codeSnippetId, String languageName, String codeSnippet) {
        this.languageName = languageName;
        this.codeSnippet = codeSnippet;
    }
}
