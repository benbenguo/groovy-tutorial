package jdbc

import groovy.sql.Sql
import word.WordTutorial

class Main {
    static main(args) {
        def db = [
            url: 'jdbc:mysql://192.168.230.14:3306/newmedia?autoreconnect=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8',
            user:'dev-user',
            password:'dev-user',
            driver:'com.mysql.jdbc.Driver'
        ]

        def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)

        def list = sql.rows("""SELECT
                                      n.title, n.content, n.sys_publishing_date
                                    FROM
                                        t_News n
                                    WHERE
                                        n.category_name = 'EDITORIAL'
                                    AND n.location = 'GLOBAL'
                                    AND lan_news = 'EN'
                                    AND status = 'PUBLISHED'
                                    AND deleted = FALSE
                                    ORDER BY
                                      sys_publishing_date DESC """)
        assert list.size() > 0

        new WordTutorial().makeWord(list)
    }
}
