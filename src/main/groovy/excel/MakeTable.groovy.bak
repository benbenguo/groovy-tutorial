package excel

import com.sun.org.apache.xpath.internal.operations.Bool
import org.apache.poi.hssf.usermodel.HSSFRow

/**
 * Created by Dean on 2017/3/3.
 */
class MakeTable {

    def fromExcel(String path) {
        def index = 0
        def tables = []
        new ExcelReader(path).eachLine{ HSSFRow row ->
            if (index != 0) {
                def curTable = row.getCell(0).getStringCellValue().trim().toUpperCase()
                if (tables.size() == 0 || !tables.last().name.equals(curTable)) {
                    def tableComments = row.getCell(1).getStringCellValue().trim()
                    tables.add(new Table(name: curTable, comments: tableComments))
                }

                def colName = row.getCell(3).getStringCellValue().trim().toUpperCase()
                def colComments = row.getCell(4).getStringCellValue().trim()
                def colType = filterType(row.getCell(6).getStringCellValue().trim().toLowerCase())
                def isNull = row.getCell(7).getStringCellValue().trim().toLowerCase().is("y")
                def isPrimaryKey = colName.equals("ID")
                tables.last().columns.add(new Column(name: colName, comments: colComments, type: colType, isNull: isNull, isPrimaryKey: isPrimaryKey))
            }
            index++
        }

//        println("count == ${index}")
        return tables
    }

    def filterType(String type) {
        if (type != null && !type.equals("")) {
            if (type.contains("number")) {
                type = "int8"
            } else if (type.contains("varchar2")) {
                type = type.replace("varchar2", "varchar")
            } else if (type.contains("clob")) {
                type = "text"
            }
        }

        return type
    }

    def makeSql(List<Table> tables) {
        def rst = ""

        for (table in tables) {
            rst += makeTable(table)
        }

        return rst
    }

    def makeTable(Table table) {
        return """
                    DROP TABLE IF EXISTS "public"."${table.name}";
                    CREATE TABLE "public"."${table.name}" (
                        ${makeColumns(table.columns)}
                    )
                    WITH (OIDS=FALSE);

                    COMMENT ON TABLE "public"."${table.name}" IS '${table.comments}';
                    ${makeColsComments(table.name, table.columns)}
                """
    }

    def makeColsComments(String tableName, List<Column> columns) {
        def rst = ""
        for (column in columns) {
            rst += makeColComments(tableName, column)
        }
        return rst
    }

    def makeColComments(String tableName, Column column) {
        return """
                    COMMENT ON COLUMN "public"."${tableName}"."${column.name}" IS '${column.comments}';
               """
    }

    def makeColumns(List<Column> columns) {
        def rst = ""
        columns.eachWithIndex{ Column column, int i ->
            boolean hasCommaCharAtEnd = true
            if (i == (columns.size() - 1)) {
                hasCommaCharAtEnd = false
            }

            rst += makeColumn(column, hasCommaCharAtEnd)
        }

        return rst
    }

    def makeColumn(Column column, hasCommaCharAtEnd) {
        return """
                "${column.name}" ${column.type}${notNull(column.isNull)}${primaryKey(column.isPrimaryKey)}${hasCommaCharAtEnd? ",": ""}
               """
    }

    def primaryKey(boolean isPrimaryKey) {
        return isPrimaryKey ? " PRIMARY KEY" : ""
    }

    def notNull(boolean isNull) {
        return isNull ? "" : " NOT NULL"
    }
}

class Table {
    String name
    String comments
    List<Column> columns = []
}

class Column {
    String name
    String type
    String comments
    Boolean isNull
    Boolean isPrimaryKey
}
