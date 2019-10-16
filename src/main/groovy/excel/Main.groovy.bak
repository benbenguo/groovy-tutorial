package excel

/**
 * Created by Dean on 2017/3/3.
 */
class Main {
    static main(args) {
        def path = "E:\\Work\\Resource\\ONS Resources\\DSK.xls"
        def mt = new MakeTable()
        def tables = mt.fromExcel(path)
        def sql = mt.makeSql(tables)

        def file = new File("E:\\Work\\Resource\\ONS Resources\\DSK.sql")
        if (!file.exists()) {
            file.createNewFile()
        }
        file.write(sql)
        println("completed")
    }
}
