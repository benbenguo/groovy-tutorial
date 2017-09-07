package json

import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import po.Person

/**
 * Created by Dean on 2017/1/14.
 * JsonBuilder which creates a data structure in memory,
 * which is handy in those situations where you want to alter the structure programmatically before output
 */
class JsonBuilderTutorial {
    static main(args) {
//        def result = {
//            def str = null
//            isNullOrEmpty(str = getString()) ? null : str
//        }
//
//        println result

//        makeJsonStringOnlyOneRootNode()
//        makeJsonFromMap()
//        makeJsonStringMuliRootNode()
        makeJsonStringIncludeArray()
        println "JsonBuilderTutorial Completed"
    }

    static isNullOrEmpty(input) {
        if (input == null || input.equals(null) || input.toString().trim() == "" || input.toString().trim().equals("")) {
            return true
        } else {
            return false
        }
    }

    def getString() {
        return "xxx"
    }

    static makeJsonStringOnlyOneRootNode() {
        def builder = new JsonBuilder()

        builder.records {
            car {
                name 'HSV Maloo'
                make 'Holden'
                year 2006
                country 'Australia'
                artist (name: 'Dean')
                record {
                    type 'speed'
                    description 'production pickup truck with speed of 271kph'
                }
            }
        }
        String result = JsonOutput.prettyPrint(builder.toString())
        println result
    }

    static makeJsonFromMap() {
        def map = [
                car: [
                    name:'HSV Maloo'
                ]
        ]
        map.car.year = 2006

        def builder = new JsonBuilder(map)
        println(builder.toPrettyString())
    }

    static makeJsonStringMuliRootNode() {
        JsonBuilder builder = new JsonBuilder()
        builder {
            name 'HSV Maloo'
            make 'Holden'
            year 2006
            country 'Australia'
        }

        String result = JsonOutput.prettyPrint(builder.toString())
        println result
    }

    static makeJsonStringIncludeArray() {
        JsonBuilder builder = new JsonBuilder()
        def list = []
        def map = [:]

        def array = [
                new Person(name: "Dean", age: 10),
                new Person(name: "Yaoyao", age: 11),
                new Person(name: "Gouzi", age: 12)
        ]

//        builder {
//            people array
//        }

//        builder {
//            people array.collect { p ->
//                people {
//                    name p.name
//                }
//            }
//        }

        builder {
            people array, { p ->
                name p.name
            }
        }

        String result = JsonOutput.prettyPrint(builder.toString())
        println result
    }
}
