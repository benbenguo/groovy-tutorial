package jdbc

import groovy.sql.Sql
import org.apache.commons.lang.RandomStringUtils

class GPTest {

    static main(args) {

        def db = [
            url: 'jdbc:pivotal:greenplum://192.168.1.51:5432;DatabaseName=zw',
            user:'gptest',
            password:'gptest',
//            url: 'jdbc:pivotal:greenplum://192.168.1.95:15430;DatabaseName=dlwdb',
//            user:'dlw',
//            password:'dlw123456',
            driver:'com.pivotal.jdbc.GreenplumDriver'
        ]

        def instance = Sql.newInstance(db.url, db.user, db.password, db.driver)

        def sql = 'INSERT INTO t1 (table_schema, table_name, column_name, data_type) VALUES (?,?,?,?)'

        def start = new Date()

        instance.withBatch(1000, sql) { ps ->
            (0..10000).each {
                ps.addBatch(
                    RandomStringUtils.randomAlphanumeric(10),
                    RandomStringUtils.randomAlphanumeric(10),
                    RandomStringUtils.randomAlphanumeric(10),
                    RandomStringUtils.randomAlphanumeric(10)
                )
            }
        }

        println("duration: ${(new Date().getTime() - start.getTime()) / 1000}")
    }
}
