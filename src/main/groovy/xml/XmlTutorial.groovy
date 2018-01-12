package xml

import groovy.xml.MarkupBuilder

/**
 * Created by Dean on 2017/2/27.
 */
class XmlTutorial {
    static main(args) {
        String str = '海口&mdash;今日科创'
        String result = filterBroadCastTitle(str, new Date())
        println("============== ${result}")
        markupBuilder()
//        streamBuilder()
    }

    private static String filterBroadCastTitle(String title, Date date) {
        def filterTitle = title.replaceAll('&mdash;', ' ')
        return filterTitle + " " + date.format('MM/dd')
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
            "youname" {
                "name"("dean")
            }
        }

//        def movie = new XmlSlurper()
//                .parseText(xmlWriter.toString())

//        movie."FromUserName".each {
//            println(it.text())
//        }
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
