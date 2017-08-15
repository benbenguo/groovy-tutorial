package json

import groovy.json.JsonOutput
import groovy.json.StreamingJsonBuilder

/**
 * Created by Dean on 2017/1/14.
 * StreamingJsonBuilder directly streams to a writer without any intermediate memory data structure.
 * If you do not need to modify the structure and want a more memory-efficient approach, use StreamingJsonBuilder.
 *
 * The usage of StreamingJsonBuilder is similar to JsonBuilder. In order to create this Json string
 */
class StreamingJsonBuilderTutorial {
    static main(args) {
        StringWriter writer = new StringWriter()
        StreamingJsonBuilder builder = new StreamingJsonBuilder(writer)
        builder.records {
            car {
                name 'HSV Maloo'
                make 'Holden'
                year 2006
                country 'Australia'
                record {
                    type 'speed'
                    description 'production pickup truck with speed of 271kph'
                }
            }
        }
        String json = JsonOutput.prettyPrint(writer.toString())
        println json
        println "StreamingJsonBuilderTutorial Completed"
    }
}
