package xstream

import xstream.annotation.SimpleType

/**
 * Created by Dean on 2017/2/28.
 */
class GroovyObjectGenerator {
    def xsdMain
    def xsdReuse
    def xsdStructure
    def types = []

    static main(args) {
        def generator = new GroovyObjectGenerator()
        generator.makeClassFile()
        println "completed"
    }

    def makeClassFile() {
        def rootPath = "E:\\\\Work\\\\Projects\\\\GaoYuan\\\\resources\\\\references\\\\上报最高院数据文件\\\\数据交换用XML Schema文件\\\\"
        def file = new File("${rootPath}11_刑事一审.xsd")
        xsdMain = new XmlParser().parse(file)
        file = new File("${rootPath}_0_结构_复用.xsd")
        xsdReuse = new XmlParser().parse(file)
        file = new File("${rootPath}_1_结构_刑事.xsd")
        xsdStructure = new XmlParser().parse(file)

        for (type in SimpleType.values()) {
            types.add(type.name())
        }

        makeMainClass()
        makeReuseClass()
        makeStructure()
    }

    def makeStructure() {
        xsdStructure."xsd:group".eachWithIndex { item, index ->
            def name = item.@name

            def code = """
            package openplatform.utils.xconverter
            
            import openplatform.utils.xconverter.annotation.CollectionElement
            import openplatform.utils.xconverter.annotation.ComplexElement
            import openplatform.utils.xconverter.annotation.Element
            import openplatform.utils.xconverter.annotation.ElementAlias
            import openplatform.utils.xconverter.annotation.GroupElement
            import openplatform.utils.xconverter.annotation.RootElement
            import openplatform.utils.xconverter.annotation.SimpleType

            /**
             * Created by Dean on 2017/2/28.
             */
            @ElementAlias(name = "${name}")
            @RootElement
            class ${name} {
                ${makeReuseProperties(item."xsd:sequence"[0])}
            }
            """

            def out = new File("D:\\domain\\${name}.groovy")
            if (!out.exists()) {
                out.createNewFile()
            }
            out.write(code)
        }

        xsdStructure."xsd:complexType".eachWithIndex { item, index ->

            if ((item."xsd:sequence"[0]?.name() == null) || item."xsd:sequence"[0].children() == 0 || (item."xsd:sequence"[0]."xsd:element"[0]?.@name == "R")) {
                return
            }

            String name = item.@name

            if (name.substring(name.length() - 1) == "1") {
                name = name.substring(0, name.length() - 1)
            }

            def code = """
            package openplatform.utils.xconverter
            
            import openplatform.utils.xconverter.annotation.CollectionElement
            import openplatform.utils.xconverter.annotation.ComplexElement
            import openplatform.utils.xconverter.annotation.Element
            import openplatform.utils.xconverter.annotation.ElementAlias
            import openplatform.utils.xconverter.annotation.GroupElement
            import openplatform.utils.xconverter.annotation.RootElement
            import openplatform.utils.xconverter.annotation.SimpleType

            /**
             * Created by Dean on 2017/2/28.
             */
            @ElementAlias(name = "${name}")
            @RootElement
            class ${name} {
                ${makeReuseProperties(item."xsd:sequence"[0])}
            }
            """

            def out = new File("D:\\domain\\${name}.groovy")
            if (!out.exists()) {
                out.createNewFile()
            }
            out.write(code)
        }
    }

    def makeReuseClass() {
        xsdReuse."xsd:group".eachWithIndex { item, index ->
            def name = item.@name

            def code = """
            package openplatform.utils.xconverter
            
            import openplatform.utils.xconverter.annotation.CollectionElement
            import openplatform.utils.xconverter.annotation.ComplexElement
            import openplatform.utils.xconverter.annotation.Element
            import openplatform.utils.xconverter.annotation.ElementAlias
            import openplatform.utils.xconverter.annotation.GroupElement
            import openplatform.utils.xconverter.annotation.RootElement
            import openplatform.utils.xconverter.annotation.SimpleType

            /**
             * Created by Dean on 2017/2/28.
             */
            @ElementAlias(name = "${name}")
            @RootElement
            class ${name} {
                ${makeReuseProperties(item."xsd:sequence"[0])}
            }
            """

            def out = new File("D:\\domain\\${name}.groovy")
            if (!out.exists()) {
                out.createNewFile()
            }
            out.write(code)
        }

        xsdReuse."xsd:complexType".eachWithIndex { item, index ->

            if ((item."xsd:sequence"[0]?.name() == null) || item."xsd:sequence"[0].children() == 0 || (item."xsd:sequence"[0]."xsd:element"[0]?.@name == "R")) {
                return
            }

            String name = item.@name

            if (name.substring(name.length() - 1) == "1") {
                name = name.substring(0, name.length() - 1)
            }

            def code = """
            package openplatform.utils.xconverter
            
            import openplatform.utils.xconverter.annotation.CollectionElement
            import openplatform.utils.xconverter.annotation.ComplexElement
            import openplatform.utils.xconverter.annotation.Element
            import openplatform.utils.xconverter.annotation.ElementAlias
            import openplatform.utils.xconverter.annotation.GroupElement
            import openplatform.utils.xconverter.annotation.RootElement
            import openplatform.utils.xconverter.annotation.SimpleType

            /**
             * Created by Dean on 2017/2/28.
             */
            @ElementAlias(name = "${name}")
            @RootElement
            class ${name} {
                ${makeReuseProperties(item."xsd:sequence"[0])}
            }
            """

            def out = new File("D:\\domain\\${name}.groovy")
            if (!out.exists()) {
                out.createNewFile()
            }
            out.write(code)
        }
    }

    def makeMainClass() {
        def name = xsdMain."CASE:element"[0].@name

        def code = """
            package openplatform.utils.xconverter
            
            import openplatform.utils.xconverter.annotation.CollectionElement
            import openplatform.utils.xconverter.annotation.ComplexElement
            import openplatform.utils.xconverter.annotation.Element
            import openplatform.utils.xconverter.annotation.ElementAlias
            import openplatform.utils.xconverter.annotation.GroupElement
            import openplatform.utils.xconverter.annotation.RootElement
            import openplatform.utils.xconverter.annotation.SimpleType

            /**
             * Created by Dean on 2017/2/28.
             */
            @ElementAlias(name = "${name}")
            @RootElement
            class ${name} {
                ${makeCaseProperties(xsdMain."CASE:complexType"."CASE:sequence"[0])}
            }
        """

        def out = new File("D:\\domain\\${name}.groovy")
        if (!out.exists()) {
            out.createNewFile()
        }
        out.write(code)
    }

    def queryType(xsd, name) {
        def type = null
        xsd."xsd:simpleType".each { it ->
            if (it.@name == name) {
                if (it."xsd:restriction"[0].@base == "xsd:nonNegativeInteger") {
                    type = "Long"
                    return
                }
            }
        }

        if (type == null) {
            xsd."xsd:complexType".each { it ->
                if (it.@name == name) {
                    if (it."xsd:sequence"."xsd:element"[0].@name == "R") {
                        type = "List"
                    } else {
                        type = "Object"
                    }
                    return
                }
            }
        }

        return type
    }

    def makeReuseProperties(node) {
        def properties = ""
        node.children().eachWithIndex { item, index ->
            def minOccurs = null
            if (item.@minOccurs != null && item.@minOccurs != "0") {
                minOccurs = item.@minOccurs
            }

            if (item.name() == "xsd:element") {
                if (types.contains(item.@type)) {
                    properties += makeElement(item.@name, item.@type, index, minOccurs)
                } else {
                    def type = queryType(xsdReuse, item.@type)

                    if (type == "Long") {
                        properties += makeElement(item.@name, "NonNegativeInteger", index, minOccurs)
                    } else if (type == "Object") {
                        properties += makeComplexElement(item.@name, item.@type, index, minOccurs)
                    } else if (type == "List") {
                        properties += makeCollectionElement(item.@name, item.@type, index, minOccurs)
                    }
                }
            } else if (item.name() == "xsd:group") {
                properties += makeGroupElement(item.@ref, index, minOccurs)
            } else if (item.name() == "xsd:choice") {
                item."xsd:group".each { group ->
                    properties += makeGroupElement(group.@ref, index, minOccurs)
                }
            }
        }

        return properties
    }

    def makeCaseProperties(node) {
        def properties = ""
        node.children().eachWithIndex { item, index ->

            def minOccurs = null
            if (item.@minOccurs != null && item.@minOccurs != "0") {
                minOccurs = item.@minOccurs
            }

            if (item.name() == "CASE:element") {

                if (types.contains(item.@type)) {
                    properties += makeElement(item.@name, item.@type, index, minOccurs)
                } else {
                    def type = queryType(xsdReuse, item.@type)

                    if (type == null) {
                        type = queryType(xsdStructure, item.@type)
                    }

                    if (type == "Long") {
                        properties += makeElement(item.@name, "NonNegativeInteger", index, minOccurs)
                    } else if (type == "Object") {
                        properties += makeComplexElement(item.@name, item.@type, index, minOccurs)
                    } else if (type == "List") {
                        properties += makeCollectionElement(item.@name, item.@type, index, minOccurs)
                    }
                }
            } else if (item.name() == "CASE:group") {
                properties += makeGroupElement(item.@ref, index, minOccurs)
            }
        }

        return properties
    }

    def makeCollectionElement(name, type, index, minOccurs) {
        def values = ""
        if (minOccurs != null) {
            values = """(minOccurs = "${minOccurs}")"""
        }

        return """
            @ElementAlias(name = "${name}")
            @CollectionElement${values}
            List<${type}> property${index}
            """
    }

    def makeComplexElement(name, type, index, minOccurs) {
        def values = ""
        if (minOccurs != null) {
            values = """(minOccurs = "${minOccurs}")"""
        }

        return """
            @ElementAlias(name = "${name}")
            @ComplexElement${values}
            ${type} property${index}
            """
    }

    def makeGroupElement(name, index, minOccurs) {
        def values = ""
        if (minOccurs != null) {
            values = """(minOccurs = "${minOccurs}")"""
        }

        return """
            @ElementAlias(name = "${name}")
            @GroupElement${values}
            ${name} property${index}
            """
    }

    def makeElement(name, simpleType, index, minOccurs) {
        def type = simpleType
        if (type in [SimpleType.C10.name(),
                     SimpleType.C20.name(),
                     SimpleType.C30.name(),
                     SimpleType.C40.name(),
                     SimpleType.C50.name(),
                     SimpleType.C60.name(),
                     SimpleType.C100.name(),
                     SimpleType.C120.name(),
                     SimpleType.C200.name(),
                     SimpleType.C240.name(),
                     SimpleType.BB.name()]) {
            type = "String"
        } else if (type in [SimpleType.D.name(),
                                  SimpleType.T.name(),
                                  SimpleType.DT.name()]) {
            type = "Date"
        } else if (type == SimpleType.N.name()) {
            type = "Integer"
        } else if (type == SimpleType.SN.name()) {
            type = "Short"
        } else if (type == SimpleType.TN.name()) {
            type = "Integer"
        } else if (type == SimpleType.F.name()) {
            type = "Float"
        } else if (type == SimpleType.M.name()) {
            type = "BigDecimal"
        } else if (type == SimpleType.NonNegativeInteger.name()) {
            type = "Long"
        }

        def values = ""
        if (minOccurs != null) {
            values = """(simpleType = SimpleType.${simpleType}, minOccurs = "${minOccurs}")"""
        } else {
            values = """(simpleType = SimpleType.${simpleType})"""
        }

        return """
            @ElementAlias(name = "${name}")
            @Element${values}
            ${type} property${index}
            """
    }
}
