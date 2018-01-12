package regex

import java.util.regex.Matcher
import java.util.regex.Pattern

class Main {
    /**
     * 正则表达式
     * @param args
     */
    static main(args) {
        def email = "251055594@qq.com"
        def regex = "[0-9]+@qq.com"

        // 1. 判断是否匹配 java
        def match = Pattern.matches(regex, email)
        if (match == true) {
            println("matched")
        }
        // 2. 判断是否匹配 groovy
        if (email ==~ regex) {
            println("matched")
        }

        String html = "<html>\r\n"   +
                "<head><title>test</title><head>\r\n"   +
                "<body>"   +
                "<P><img   height=\"100\"       src='abc.png'   weight=\"30\">abcdefg"   +
                "<img   src='http://abc.xyz.com/123/456.jpg'   /><br>"   +
                "<img   height=\"100\"       \r\n"   +
                "       src=\"abc.jpg\"   \r\n"   +
                "   weight=\"30\">abcdefg         \r\n"   +
                "   <img   src='ttt.jpg'>"   +
                "   <img   src='123.jpg' />"   +
                "</body></html>"

        regex = "<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)"

        // 3. 提取图片标签 java
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(regex)
        // 现在创建 matcher 对象
        Matcher m = r.matcher(html)
        while(m.find()) {
            println(m.group(0))
            println(m.group(1))
        }

        // 4. 提取图片标签 groovy
        println("groovy 4")
        (html =~ regex).each {
            println(it[0])
            println(it[1])
        }
    }
}
