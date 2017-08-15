package http

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.ByteArrayBody
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.impl.client.HttpClientBuilder

/**
 * Created by Dean on 2017/6/12.
 */
class Main {
    static void main(args) {
//        testPost()
        testMultipartPost()
    }

    static void testAvro() {
        def url = 'http://127.0.0.1:8888/service?_service=obj.sample&_format=avro&_token&_name=dean'
        def get = new HttpGet(url)
        def client = HttpClientBuilder.create().build()
        def response = client.execute(get)
        def is = response.entity.content
    }

    static void testHttp() {
        def map = [:]
        map["name"] = "Maritime DevCon"
        map["address"] = "Fredericton"
        map["handle"] = "maritimedevcon"

        def jsonBody = new JsonBuilder(map).toString()

// build HTTP POST

        def url = 'http://151.0.40.179:8680/analyze/elements?caseno=334920170301000008'
        def post = new HttpGet(url)

        post.addHeader("content-type", "application/json")
//        post.setEntity(new StringEntity(jsonBody))

// execute

        def client = HttpClientBuilder.create().build()
        def response = client.execute(post)

        def bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
        def jsonResponse = bufferedReader.getText()
        println "response: \n" + jsonResponse

        def slurper = new JsonSlurper()
        def resultMap = slurper.parseText(jsonResponse)

        assert "Maritime DevCon" == resultMap["name"]
        assert resultMap["id"] != null
    }

    static void testMultipartPost() {
        def url = 'http://localhost:66/service'
        def http = new HTTPBuilder( url)
        def file = new File("D:/jdk.bat")

        http.request(Method.POST) { request ->
            def entity = new MultipartEntityBuilder()
            entity.addPart("file1", new ByteArrayBody(file.bytes, file.name))
            entity.addPart("name", new StringBody("yyyy"))
            request.entity = entity.build()
            response.success = { resp, data ->
                println data
            }
        }
    }

    static void testPost() {
        def url = 'http://localhost:66/service'
        def http = new HTTPBuilder( url)
        http.post(body: [name: 'bob']) { resp ->
            println "POST Success: ${resp.statusLine}"
        }
    }
}