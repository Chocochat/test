package com.aws.codestar.projecttemplates.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ErrorMsgTransformer {

    private ErrorMsgTransformer() {
    }

    public static String createError(String errorType, int statusCode, String requestId, String message) {

        try {
            Map<String, Object> errorPayload = new HashMap<>();
            errorPayload.put("errorType", errorType);
            errorPayload.put("httpStatus", statusCode);
            errorPayload.put("requestId", requestId);
            errorPayload.put("message", message);
            return new ObjectMapper().writeValueAsString(errorPayload);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException {}", e);
            return String.format("{\"errorType\":\"InternalServerError\",\"httpStatus\":500,\"requestId\":\"%s\",\"message\":\"JsonProcessingException occurred.\"}", requestId);
        }
    }
}