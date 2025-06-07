package com.airtribe.coderank.util;

import com.airtribe.coderank.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

public class ExecutionUtils {

    /**
     *
     * @param langName
     * @return
     */
    public static String getImageNameByLanguage(String langName){
        Map<String, String> langMap = new HashMap<>();
        langMap.put("python", "python-image");
        langMap.put("java", "java-image");
        langMap.put("javascript", "javascript-image");
        langMap.put("cpp", "cpp-image");


        return langMap.get(langName);
    }


}
