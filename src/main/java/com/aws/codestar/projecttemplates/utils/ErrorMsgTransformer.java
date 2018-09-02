package com.aws.codestar.projecttemplates.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ErrorMsgTransformer {

    private ErrorMsgTransformer() {
    }

    public static String createError(String body, int header, String statusCode) {

        try {
            Map<String, Object> errorPayload = new HashMap<>();
            errorPayload.put("httpStatus", statusCode);
            errorPayload.put("header", header);
            errorPayload.put("body", body);
            return new ObjectMapper().writeValueAsString(errorPayload);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException {}", e);
            return String.format("{\"errorType\":\"InternalServerError\",\"httpStatus\":500,\"requestId\":\"%s\",\"message\":\"JsonProcessingException occurred.\"}");
        }
    }
}