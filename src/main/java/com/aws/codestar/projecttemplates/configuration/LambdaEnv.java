//package com.aws.codestar.projecttemplates.configuration;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Getter
//@Setter
//@Component
//public class LambdaEnv {
//
//    @Value("${dataUrl}")
//    private String dataUrl;
//
//    public Map<String, String> getEnvMap() {
//        Map<String, String> env = new HashMap<>();
//        env.put("dataUrl", this.dataUrl);
//        return env;
//    }
//}
