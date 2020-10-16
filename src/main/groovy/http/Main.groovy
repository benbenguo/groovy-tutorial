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
        testPost()
    }

    static request() {
        def url = 'http://127.0.0.1:8200/user/user/userInfo?username=root'

        def get = new HttpGet(url)
        get.setHeader('cbd-format', 'json')
        def client = HttpClientBuilder.create().build()
        def response = client.execute(get)
        def result = resultJson(response)
        println(result)
    }

    static def resultJson(response) {
        def bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
        def jsonResponse = bufferedReader.getText()
        println "response: \n" + jsonResponse

        def slurper = new JsonSlurper()
        return slurper.parseText(jsonResponse)
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
        def url = 'http://service.315i.com:3880/user/info'
        def http = new HTTPBuilder(url)

        http.post(body: [
            userId: '28899acd62a44957b51fe58a6c232e61',
            prodId: 'V003CFF9CFA866A1E0438760007FE88C']) { resp, data ->

            println "POST Success: ${resp.statusLine}"
        }
    }

    static void testGet() {
        def url = 'http://opennews.com.cn/opennews/downloadInterface/getDownloadNewsList'
        new HTTPBuilder(url).get([params: [lastId: 0, category: "SPORTS"]]) { response, data ->
            println "completed"
        }
    }
}
