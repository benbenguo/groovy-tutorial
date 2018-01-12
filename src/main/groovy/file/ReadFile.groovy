package file

import groovy.xml.MarkupBuilder

class ReadFile {
    static main(args) {
        def path = "F:\\Temp\\cishu\\c2.txt"
        def reader = new File(path).newReader('UTF-8')

//        filterText(reader)
        handleFilteredText()

//        def list = createData(reader)
//        createXml(list, "F:\\Temp\\cishu\\r2.xml")
    }

    static handleFilteredText() {
        def path = "F:\\Temp\\cishu\\filtered.txt"
        def reader = new File(path).newReader('UTF-8')

        def result = []
        reader.eachLine { line ->
            line = line.trim()
            if (line != "") {
                def filterCont = line =~ ".*?([\\u4e00-\\u9fa5]+?).*?"

                if (filterCont != null && filterCont.size() != 0 && filterCont[0][1].trim() != "") {
                    def title = filterCont[0][1].trim()
                    line.replace(filterCont[0][1], "【" + title + "】")
                    result.add(line)
                }
            }
        }
    }

    static filterText(BufferedReader reader) {
        def result = []
        reader.eachLine { line ->
            line = line.trim()
            if (line ==~ ".*【.*】.*") {
                def rText = line =~ ".*?【(.*?)】.*?"
                def cDefinition = rText.size()
                if (cDefinition > 1) {
                    result << line
                }
            } else {
                result << line
            }
        }

        def file = new File("F:\\Temp\\cishu\\filtered.txt")
        !file.exists() ? file.createNewFile() : null

        file.text = result.join(System.getProperty("line.separator"))

        return result
    }

    static createXml(List list, String path) {
        def xmlWriter = new StringWriter()
        def xmlMarkup = new MarkupBuilder(xmlWriter)

        xmlMarkup."root" {
            "headwords" {
                list.each { item ->
                    "headword" {
                        "text"(item.text)
                        "pinyin"(item.pinyin)
                        "definitions" {
                            item.definitions.each { dItem ->
                                "definition"(dItem)
                            }
                        }
                    }
                }
            }
        }

        def file = new File(path)
        file.write(xmlWriter.toString())
    }

    static List createData(BufferedReader reader) {
        def result = []
        reader.eachLine { line ->
            line = line.trim()
            if (line ==~ ".*【.*】.*") {
                def rText = line =~ ".*?【(.*?)】.*?"
                def cDefinition = rText.size()

                def list = line.split("【(.*?)】")
                def offset = 0
                if (list.size() > cDefinition) {
                    offset = 1
                }

                for (int i = 0; i < cDefinition; i++) {
                    def text = null
                    def pinyin = null
                    def definitions = []

                    text = rText[i][1]
                    def cont = list[i + offset]

                    def filterCont = cont =~ ".*?([\\u4e00-\\u9fa5]+?).*?"
                    def bRst = cont.substring(0, cont.indexOf(filterCont[0][1])) ==~ ".*〔\\d+〕.*"

                    if (bRst) {
                        def rPinyin = cont =~ "(.*?)〔.*?"
                        pinyin = rPinyin[0][1]

                        def numList = []
                        def rNumber = cont =~ ".*?〔(\\d+)〕.*?"
                        rNumber.each { item ->
                            numList.add(item[1])
                        }

                        for (int j = 0; j < numList.size(); j++) {
                            def definition = null
                            if (j == numList.size() - 1) {
                                definition = cont.substring(cont.indexOf("〔${numList.get(j)}〕"))
                            } else {
                                try {
                                    definition = cont.substring(cont.indexOf("〔${numList.get(j)}〕"), cont.indexOf("〔${numList.get(j + 1)}〕"))
                                } catch (err) {
                                    println(definition)
                                    throw err
                                }
                            }
                            definitions.add(definition?.trim())
                        }
                    } else {
                        def rPinyin = cont =~ "([^\\u4e00-\\u9fa5]*?)[\\u4e00-\\u9fa5].*?"
                        pinyin = rPinyin[0][1]
                        def definition = cont.substring(cont.indexOf(pinyin) + pinyin.length())
                        definitions.add(definition?.trim())
                    }

                    if (text != null && text.trim() != "") {
                        result.add([
                                text: text.trim(),
                                pinyin: pinyin?.trim(),
                                definitions: definitions
                        ])
                    }
                }
            }
        }

        return result
    }
}
