package xstream

import groovy.xml.MarkupBuilder
import xstream.annotation.CollectionElement
import xstream.annotation.ComplexElement
import xstream.annotation.Element
import xstream.annotation.ElementAlias
import xstream.annotation.GroupElement
import xstream.annotation.RootElement
import xstream.exception.ComplexElementNotFoundException
import xstream.exception.SizeOutMaxOccursException
import xstream.exception.TypeNotFoundException

/**
 * Created by Dean on 2017/2/27.
 */
class XmlBuilder {

    final types = [
            Short,
            Integer,
            Float,
            Long,
            BigDecimal,
            Double,
            Date,
            String
    ]

    def builder(instance) {
        def xmlWriter = new StringWriter()
        def xmlMarkup = new MarkupBuilder(xmlWriter)

        createXml(xmlMarkup, instance)

        def file = new File("D:/file.xml")
        file.write(xmlWriter.toString())
    }

    def addTags = { instance ->
        Class<?> clazz = instance.class

        for (field in clazz.declaredFields) {

            def subTag = field.isAnnotationPresent(ElementAlias) ? field.getAnnotation(ElementAlias).name() : field.name
            def value = instance."${field.name}"

            if (field.isAnnotationPresent(Element)) {
                if (value == null) {
                    if (field.getAnnotation(Element).minOccurs() > 0) {
                        "${subTag}"()
                    }
                    continue
                }

                if (field.type in types) {
                    "${subTag}"(value)
                } else {
                    throw new TypeNotFoundException("Class: ${clazz.simpleName}, field: ${field.name}, field's type is not found in specified types, field's type is ${field.type}")
                }
            } else if (field.isAnnotationPresent(CollectionElement)) {
                def list = (List) value
                if (value == null && list.size() == 0) {
                    if (field.getAnnotation(CollectionElement).minOccurs() > 0) {
                        "${subTag}"()
                    }

                    continue
                }

                def maxOccurs = field.getAnnotation(CollectionElement).maxOccurs()
                if (list.size() > maxOccurs) {
                    throw new SizeOutMaxOccursException("Class: ${clazz.simpleName}, field: ${field.name}, list size out maxOccurs, maxOccurs is ${maxOccurs}")
                } else {
                    def isR = field.getAnnotation(CollectionElement).isR()
                    if (isR.is(true)) {
                        "${subTag}" {
                            for(item in list) {
                                "R" {
                                    addTags.delegate = delegate
                                    addTags(item)
                                }
                            }
                        }
                    } else {
                        for(item in list) {
                            "${subTag}" {
                                addTags.delegate = delegate
                                addTags(value)
                            }
                        }
                    }
                }
            } else if (field.isAnnotationPresent(ComplexElement)) {
                if (value == null) {
                    if (field.getAnnotation(ComplexElement).minOccurs() > 0) {
                        "${subTag}"()
                    }

                    continue
                }

                "${subTag}" {
                    addTags.delegate = delegate
                    addTags(value)
                }
            } else if (field.isAnnotationPresent(GroupElement)) {
                if (value == null) {
                    continue
                }

                addTags.delegate = delegate
                addTags(value)
            }
        }
    }

    private createXml(xmlNode, obj) {
        Class<?> clazz = obj.class
        if (!clazz.isAnnotationPresent(RootElement)) {
            throw new ComplexElementNotFoundException("Class ${clazz.simpleName} is not defined annotation 'ComplexElement'")
        }

        def tag = clazz.isAnnotationPresent(ElementAlias) ?
                clazz.getAnnotation(ElementAlias).name() : clazz.simpleName

        xmlNode."${tag}" {
            addTags.delegate = delegate
            addTags(obj)
        }
    }
}
