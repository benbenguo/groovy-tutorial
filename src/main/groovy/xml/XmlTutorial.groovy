package xml

import groovy.xml.MarkupBuilder

/**
 * Created by Dean on 2017/2/27.
 */
class XmlTutorial {
    static main(args) {
//        markupBuilder()
        streamBuilder()
    }

    static def markupBuilder() {
        def xmlWriter = new StringWriter()
        def xmlMarkup = new MarkupBuilder(xmlWriter)

        def createSubTag = {
            "案件标识"("123242345235")
        }

        def dean = "dean"

        xmlMarkup."xml" {
            createSubTag.delegate = delegate
            createSubTag()
            "ToUserName"("${dean}")
            "FromUserName"("alice")
            "FromUserName"("jack")
        }

        def movie = new XmlSlurper()
                .parseText(xmlWriter.toString())

        movie."FromUserName".each {
            println(it.text())
        }
//        def file = new File("D:/file.xml")
//        file.write(xmlWriter.toString())

        println(xmlWriter.toString())
    }

    static def streamBuilder() {
        def xmlWriter = new StringWriter()
        def builder = new groovy.xml.StreamingMarkupBuilder()

        def comment = "<![CDATA[<!-- address is new to this release -->]]>"

        def xml = {
            xml {
                ToUserName("<![CDATA[dean]]")
                FromUserName("<![CDATA[alice]]")
            }
        }

        xmlWriter << builder.bind{ mkp.yieldUnescaped xml }
        println(xmlWriter.toString())
    }


}
