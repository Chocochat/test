package com.aws.codestar.projecttemplates.model;

import lombok.Getter;
import org.springframework.http.HttpHeaders;

/**
 * POJO containing response object for API Gateway.
 */

@Getter
public class ErrorGatewayResponse extends Throwable {

    private  String body;
    private HttpHeaders headers;
    private int statusCode;

    public ErrorGatewayResponse( String body, HttpHeaders headers, int statusCode) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }
}
