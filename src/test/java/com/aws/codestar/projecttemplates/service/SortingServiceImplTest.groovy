package com.aws.codestar.projecttemplates.service

import com.aws.codestar.projecttemplates.model.ErrorGatewayResponse
import com.aws.codestar.projecttemplates.utils.HttpUtils
import com.google.gson.JsonParser
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class SortingServiceImplTest extends Specification {

    def httpUtils = Mock(HttpUtils)
    def responseEntity = Mock(ResponseEntity)

    def "test getSortedData for success data"() {
        given: 'valid request'
        def sortingServiceImpl = Spy(SortingServiceImpl, constructorArgs: [])
        def reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/inputdata.json"), "UTF-8"))
        def request = new JsonParser().parse(reader)
        def responseReader = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/responsedata.json"), "UTF-8"))
        def response = new JsonParser().parse(responseReader)
        when: 'get external data and do comparision'
        def gatewayResponse = sortingServiceImpl.getSortedData("4567-5467")
        then: 'return valid response'
        1 * sortingServiceImpl.getHttpUtils() >> httpUtils
        1 * httpUtils.getResponse(_, _) >> responseEntity
        2 * responseEntity.getStatusCodeValue() >> 200
        1 * responseEntity.getBody() >> request.toString()

        gatewayResponse.statusCode == 200
        gatewayResponse.body == response.toString()
    }

    def "test getSortedData for malformed or error data "() {
        given: 'valid request'
        def sortingServiceImpl = Spy(SortingServiceImpl, constructorArgs: [])
        when: 'get external data and do comparision'
        def gatewayResponse = sortingServiceImpl.getSortedData("4567-5467")
        then: 'return valid response'
        1 * sortingServiceImpl.getHttpUtils() >> httpUtils
        1 * httpUtils.getResponse(_, _) >> responseEntity
        2 * responseEntity.getStatusCodeValue() >> 400

        def exception = thrown(ErrorGatewayResponse)
        exception.statusCode == 400
        exception.body == "{\"Error\":\"error in getting data\"}"
    }
}