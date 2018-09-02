package com.aws.codestar.projecttemplates.utils

import com.aws.codestar.projecttemplates.model.NamesWithCars
import com.google.gson.JsonParser
import spock.lang.Specification

class CommonUtilsTest extends Specification {
    def "test redeivedDataClueanUp"() {
        given:
        def request = new JsonParser().parse("[{\"name\":\"Bradley\",\"cars\":[{\"brand\":\"MG\",\"colour\":\"Blue\"}]},{\"name\":\"Demetrios\",\"cars\":[{\"brand\":\"Toyota\",\"colour\":\"Green\"},{\"brand\":\"Holden\",\"colour\":\"Blue\"}]},{\"name\":\"Brooke\",\"cars\":[{\"brand\":\"Holden\",\"colour\":\"\"}]},{\"name\":\"Kristin\",\"cars\":[{\"brand\":\"Toyota\",\"colour\":\"Blue\"},{\"brand\":\"Mercedes\",\"colour\":\"Green\"},{\"brand\":\"Mercedes\",\"colour\":\"Yellow\"}]},{\"name\":\"Andre\",\"cars\":[{\"brand\":\"BMW\",\"colour\":\"Green\"},{\"brand\":\"Holden\",\"colour\":\"Black\"}]},{\"cars\":[{\"brand\":\"Mercedes\",\"colour\":\"Blue\"}]},{\"name\":\"\",\"cars\":[{\"brand\":\"Mercedes\",\"colour\":\"Red\"},{\"brand\":\"Mercedes\",\"colour\":\"Blue\"}]},{\"name\":\"Matilda\",\"cars\":[{\"brand\":\"Holden\"},{\"brand\":\"BMW\",\"colour\":\"Black\"}]},{\"name\":\"Iva\",\"cars\":[{\"brand\":\"Toyota\",\"colour\":\"Purple\"}]},{\"cars\":[{\"brand\":\"Toyota\",\"colour\":\"Blue\"},{\"brand\":\"Mercedes\",\"colour\":\"Blue\"}]}]").getAsJsonArray()

        when:
        def list = new CommonUtils().redeivedDataClueanUp(request)
        then:
        list.size() == 10
        list[0].getBrand() == "MG"
        list[1].getBrand() == "Toyota"
        list[2].getBrand() == "Holden"
    }

    def "test doSortingWithBrandAndthencolor"() {
        given: 'some data'
        def list = new ArrayList();
        def data1 = new NamesWithCars()
        data1.setBrand("MG")
        data1.setColor("Blue")
        data1.setName("Bradley")
        def data2 = new NamesWithCars()
        data2.setBrand("BMW")
        data2.setColor("Green")
        data2.setName("Demetrios")
        list.add(data1)
        list.add(data2)
        when: 'perform the comparision'
        def response = new CommonUtils().doSortingWithBrandAndthencolor(list)
        then:
        response[0].getBrand() == "BMW"
        response[0].getColor() == "Green"
        response[0].getName() == "Demetrios"
        response[1].getBrand() == "MG"
        response[1].getColor() == "Blue"
        response[1].getName() == "Bradley"
    }

    def "test createSimpleMapWithSortedData"() {
        given:
        def list = new ArrayList()
        def data1 = new NamesWithCars()
        data1.setBrand("BMW")
        data1.setColor("Blue")
        data1.setName("Bradley")
        def data2 = new NamesWithCars()
        data2.setBrand("BMW")
        data2.setColor("Green")
        data2.setName("Demetrios")
        list.add(data1)
        list.add(data2)
        when:'perform the comparision'
        def response = new CommonUtils().createSimpleMapWithSortedData(list)
        then: 'check the ascending data'
        response.containsKey("BMW")
        ArrayList<String> arrayList = response.get("BMW")
        arrayList.get(0) == "Bradley"
        arrayList.get(1) ==  "Demetrios"
    }
}
