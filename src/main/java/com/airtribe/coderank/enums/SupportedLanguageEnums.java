package com.airtribe.coderank.enums;


public enum SupportedLanguageEnums {
    JAVA("java"),
    PYTHON("python"),
    CPP("C++"),
    Javascript("javascript");

    private final String value;
    SupportedLanguageEnums(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
