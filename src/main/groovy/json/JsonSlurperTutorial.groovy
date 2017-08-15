package json

import groovy.json.JsonOutput
import groovy.json.JsonParserType
import groovy.json.JsonSlurper
import po.Person

/**
 * Created by Dean on 2017/1/13.
 * http://groovy-lang.org/json.html
 */
class JsonSlurperTutorial {

    static main(args) {
//        jsonSlurper()
        jsonOutput()
        println "JsonSlurperTutorial Completed"
    }

    /**
     * JsonSlurper is a class that parses JSON text or reader content
     * into Groovy data structures (objects)
     * such as maps, lists and primitive types like Integer, Double, Boolean and String.
     */
    static jsonSlurper() {
        def jsonSlurper = new JsonSlurper()

        // convert json object include the common object
        def object = jsonSlurper.parseText('{ "name": "John Doe" } /* some comment */')
        assert object instanceof Map
        assert object.name == 'John Doe'

        // convert json object include the list
        object = jsonSlurper.parseText('{ "myList": [4, 8, 15, 16, 23, 42] }')
        assert object instanceof Map
        assert object.myList instanceof List
        assert object.myList == [4, 8, 15, 16, 23, 42]

        //The JSON standard supports the following primitive data types:
        // string, number, object, true, false and null.
        // JsonSlurper converts these JSON types into corresponding Groovy types.
        object = jsonSlurper.parseText '''
        { "simple": 123,
          "fraction": 123.66,
          "exponential": 123e12
        }'''

        assert object instanceof Map
        assert object.simple.class == Integer
        assert object.fraction.class == BigDecimal
        assert object.exponential.class == BigDecimal

        // The JsonParserUsingCharacterSource is a special parser for very large files
        jsonSlurper = new JsonSlurper(type: JsonParserType.CHARACTER_SOURCE)
        object = jsonSlurper.parseText('{ "myList": [4, 8, 15, 16, 23, 42] }')

        assert object instanceof Map
        assert object.myList instanceof List
        assert object.myList == [4, 8, 15, 16, 23, 42]
    }

    /**
     * JsonOutput is responsible for serialising Groovy objects into JSON strings.
     * It can be seen as companion object to JsonSlurper, being a JSON parser.
     */
    static jsonOutput() {
        def json = JsonOutput.toJson([name: 'John Doe', age: 42])
        assert json == '{"name":"John Doe","age":42}'

        // JsonOutput does not only support primitive, maps or list data types to be serialized to JSON,
        // it goes further and even has support for serialising POGOs, that is, plain-old Groovy objects.
        json = JsonOutput.toJson([new Person(name: 'John', age: 30), new Person(name: 'Max', age: 32) ])
        assert json == '[{"name":"John"},{"name":"Max"}]'

        // As we saw in previous examples,
        // the JSON output is not pretty printed per default.
        // However, the prettyPrint method in JsonOutput comes to rescue for this task.
        json = JsonOutput.toJson([name: 'John Doe', age: 42])
        assert json == '{"name":"John Doe","age":42}'
        assert JsonOutput.prettyPrint(json) == '''\
        {
            "name": "John Doe",
            "age": 42
        }'''.stripIndent()
    }
}
