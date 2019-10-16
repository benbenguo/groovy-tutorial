package word


import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.wml.*

class WordTutorial {
    static main(args) {
        new WordTutorial().makeWord([])
    }

    void makeWord(List list) {
        // Create the package
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage()

        // Create the main document part (word/document.xml)
        MainDocumentPart wordDocumentPart = wordMLPackage.getMainDocumentPart()

        if (list == null || list.size() == 0) {
            addParaAtIndex(wordDocumentPart, "上海为外国科创人才在沪居留工作创造更加便利条件", 0, "title")
            addParaAtIndex(wordDocumentPart, new Date(), 1, "date")
            addParaAtIndex(wordDocumentPart, "评论员科建", 2, "content")
            addParaAtIndex(wordDocumentPart, """最近在沪参与建设上海科创中心的外国专家、科技人员赞扬上海市公安局在公安部的支持下，
发布了《上海科创中心系列出入境政策实施细则》与《上海科技创新中心系列出入境政策配套措施》。特别是外国专家和科技人员原持有的永久居留证，现符合条件可改领永久居留身份证，凭永居身份证可办理教育、医疗、银行、交通等手续。更受到外国专家和科创人员欢迎的，最近上海市出入境管理局制作的新媒体宣传片，该片用动画的形式介绍了各项新政策以及如何方便办理的具体办法。上海建设全球有影响的科创中心，建设世界性城市，更应广聚全球人才，为国际性人才创造更加便利的渠道来沪工作、生活，为让他们定居上海多作贡献提供良好的医疗、教育、文体等条件。借鉴上海市公安局为国际人才服务的热情态度、有效举措、注重服务对像实际获得感的经验，政府各部门都应为增强上海这座国际城市的吸引力和竞争力作出更大的贡献。""", 3, "content")
            addParaAtIndex(wordDocumentPart, " ", 4, "\\r\\n")

            addParaAtIndex(wordDocumentPart, "上海为外国科创人才在沪居留工作创造更加便利条件", 0, "title")
            addParaAtIndex(wordDocumentPart, new Date(), 1, "date")
            addParaAtIndex(wordDocumentPart, "评论员科建", 2, "content")
            addParaAtIndex(wordDocumentPart, """最近在沪参与建设上海科创中心的外国专家、科技人员赞扬上海市公安局在公安部的支持下，
发布了《上海科创中心系列出入境政策实施细则》与《上海科技创新中心系列出入境政策配套措施》。特别是外国专家和科技人员原持有的永久居留证，现符合条件可改领永久居留身份证，凭永居身份证可办理教育、医疗、银行、交通等手续。更受到外国专家和科创人员欢迎的，最近上海市出入境管理局制作的新媒体宣传片，该片用动画的形式介绍了各项新政策以及如何方便办理的具体办法。上海建设全球有影响的科创中心，建设世界性城市，更应广聚全球人才，为国际性人才创造更加便利的渠道来沪工作、生活，为让他们定居上海多作贡献提供良好的医疗、教育、文体等条件。借鉴上海市公安局为国际人才服务的热情态度、有效举措、注重服务对像实际获得感的经验，政府各部门都应为增强上海这座国际城市的吸引力和竞争力作出更大的贡献。""", 3, "content")
            addParaAtIndex(wordDocumentPart, """科技人员原持有的永久居留证，现符合条件可改领永久居留身份证，凭永居身份证可办理教育、医疗、银行、交通等手续。更受到外国专家和科创人员欢迎的，最近上海市出入境管理局制作的新媒体宣传片，该片用动画的形式介绍了各项新政策以及如何方便办理的具体办法。上海建设全球有影响的科创中心，建设世界性城市，更应广聚全球人才，为国际性人才创造更加便利的渠道来沪工作、生活，为让他们定居上海多作贡献提供良好的医疗、教育、文体等条件。借鉴上海市公安局为国际人才服务的热情态度、有效举措、注重服务对像实际获得感的经验，政府各部门都应为增强上海这座国际城市的吸引力和竞争力作出更大的贡献。""", 4, "content")
            addParaAtIndex(wordDocumentPart, " ", 5, "content")
//            addBroadCast(wordDocumentPart, "Hello Word!", "2017-08-11", """This is a subtitle!\r\nadfasdfasdf""")
//            addBroadCast(wordDocumentPart, "Hello Word!", "2017-08-11", "This is a subtitle!")
        } else {
            list.each {
                int index = 0
                addParaAtIndex(wordDocumentPart, it.title, index++, "title")
                addParaAtIndex(wordDocumentPart, it.sys_publishing_date, index++, "date")
                List contets = getContents(it.content)

                contets.each { content ->
                    addParaAtIndex(wordDocumentPart, content, index++, "content")
                }

                addParaAtIndex(wordDocumentPart, "\r\n", index, "content")
//                addBroadCast(wordDocumentPart, it.title, it.sys_publishing_date.format("yyyy-MM-dd"), it.content)
            }
        }

        // Save it
        wordMLPackage.save(new java.io.File("D:\\Temp\\helloworld.docx"))
    }

    List<String> getContents(String content) {
        def clone = content.replaceAll("<p.*?>", "")
        clone = clone.replaceAll("<span.*?>", "")
        clone = clone.replaceAll("</span.*?>", "")
        clone = clone.replaceAll("&nbsp;", "")
        clone = clone.replaceAll("<br/>", "")

        def list = clone.split("</p>")
        return list.toList()
    }

    void addBroadCast(MainDocumentPart mdp, String title, String date, String content) {
        mdp.addStyledParagraphOfText("Title", title)
        mdp.addStyledParagraphOfText("Subtitle", date)
        mdp.addStyledParagraphOfText("Content", content)
    }

    org.docx4j.wml.P addParaAtIndex(MainDocumentPart mdp, value, int index, String type) {
        P para = null
        if (type == "title") {
            para = handleTitle(value)
        } else if (type == "date") {
            para = handleDate(value)
        } else {
            para = handleContent(value)
        }

        mdp.getContent().add(index, para)
        return para
    }

    P handleTitle(String text) {
        org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory()
        P para = factory.createP()

        PPr pPr = getPPr(para)
        ParaRPr paraRPr = getParaRPr(pPr)
        PPrBase.Spacing spacing = getSpacing(pPr)
        PPrBase.Ind ind = getInd(pPr)

        HpsMeasure size = new HpsMeasure()
        size.setVal(new BigInteger(28))
        CTLanguage lan =  new CTLanguage()
        lan.setEastAsia("zh-CN")
        paraRPr.setSz(size)
        paraRPr.setSzCs(size)
        paraRPr.setLang(lan)

        spacing.setBefore(new BigInteger(10 * 2 * 10))
        spacing.setAfter(new BigInteger(10 * 2 * 5))

        if (text != null) {
            org.docx4j.wml.R run = factory.createR()
            RPr rPr = getRPr(run)
            rPr.setSz(size)
            rPr.setSzCs(size)
            rPr.setLang(lan)

            org.docx4j.wml.Text t = factory.createText()
            t.setValue(text)
            run.getContent().add(t)
            para.getContent().add(run)
        }

        return para
    }

    P handleDate(Date date) {
        org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory()
        P para = factory.createP()

        PPr pPr = getPPr(para)
        ParaRPr paraRPr = getParaRPr(pPr)
        PPrBase.Spacing spacing = getSpacing(pPr)

        CTLanguage lan =  new CTLanguage()
        lan.setEastAsia("zh-CN")
        paraRPr.setLang(lan)

        spacing.setBefore(new BigInteger(0))
        spacing.setAfter(new BigInteger(0))

        if (date != null) {
            String strDate = date.format("yyyy-MM-dd")
            org.docx4j.wml.R run = factory.createR()
            RPr rPr = getRPr(run)
            rPr.setLang(lan)

            org.docx4j.wml.Text t = factory.createText()
            t.setValue(strDate)
            run.getContent().add(t)
            para.getContent().add(run)
        }

        return para
    }

    P handleContent(String text) {
        org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory()
        P para = factory.createP()

        PPr pPr = getPPr(para)
        ParaRPr paraRPr = getParaRPr(pPr)
        PPrBase.Spacing spacing = getSpacing(pPr)
        PPrBase.Ind ind = getInd(pPr)

        CTLanguage lan =  new CTLanguage()
        lan.setEastAsia("zh-CN")
        paraRPr.setLang(lan)

        spacing.setBefore(new BigInteger(0))
        spacing.setAfter(new BigInteger(0))
        ind.setFirstLine(new BigInteger(420))

        if (text != null) {
            org.docx4j.wml.R run = factory.createR()
            RPr rPr = getRPr(run)
            rPr.setLang(lan)

            org.docx4j.wml.Text t = factory.createText()
            t.setValue(text)
            run.getContent().add(t)
            para.getContent().add(run)
        }

        return para
    }

    PPrBase.Ind getInd(PPr pPr) {
        PPrBase.Ind  ind = pPr.getInd()

        if (ind == null) {
            ind = new PPrBase.Ind()
            pPr.setInd(ind)
        }

        return ind
    }

    PPrBase.Spacing getSpacing(PPr pPr) {
        PPrBase.Spacing spacing = pPr.getSpacing()

        if (spacing == null) {
            spacing = new PPrBase.Spacing()
            pPr.setSpacing(spacing)
        }
        return spacing
    }

    ParaRPr getParaRPr(PPr ppr) {
        ParaRPr parRpr = ppr.getRPr()
        if (parRpr == null) {
            parRpr = new ParaRPr()
            ppr.setRPr(parRpr)
        }
        return parRpr
    }

    RPr getRPr(R r) {
        RPr rpr = r.getRPr()
        if (rpr == null) {
            rpr = new RPr()
            r.setRPr(rpr)
        }
        return rpr
    }

    static PPr getPPr(P p) {
        PPr ppr = p.getPPr()

        if (ppr == null) {
            ppr = new PPr()
            p.setPPr(ppr)
        }

        return ppr
    }
}
