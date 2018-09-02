package com.aws.codestar.projecttemplates.utils

import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import java.lang.invoke.MethodHandleImpl

import static org.springframework.http.HttpMethod.GET

class HttpUtilsTest extends Specification {

    def restTemplate = Mock(RestTemplate)
    def responseEntity = Mock(ResponseEntity)
    def httpClientErrorException = Mock(HttpClientErrorException)
    def ex = Mock(Exception)

    def "test getResponse"() {
        given:
        def httpUtils = Spy(HttpUtils, constructorArgs: [])
        when:
        def responseEntityh = httpUtils.getResponse("http://localhost/prod", String.class)
        then:
        1 * httpUtils.getRestTemplate() >> restTemplate
        1 * restTemplate.exchange(_, _, _ , _) >> responseEntity
        1 * responseEntity.getStatusCodeValue() >> 200
        1 * responseEntity.getBody() >> "data"

        responseEntity != null
    }

    def "test getResponse "() {
        given:
        def httpUtils = Spy(HttpUtils, constructorArgs: [])
        when:
        def responseEntityh = httpUtils.getResponse("http://localhost/prod", String.class)
        then:
        1 * httpUtils.getRestTemplate() >> restTemplate
        1 * restTemplate.exchange(_, _, _ , _) >> responseEntity
        1 * responseEntity.getStatusCodeValue() >> 400
        1 * responseEntity.getBody() >> "error"

        responseEntity != null

    }
}
