package com.airtribe.coderank.dto;

public class LanguageNotFoundDTO {
    private static String message;

    public LanguageNotFoundDTO(){
    }
    public LanguageNotFoundDTO(String message) {
        this.message = message;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        LanguageNotFoundDTO.message = message;
    }
}
