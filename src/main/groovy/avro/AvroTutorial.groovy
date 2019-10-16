package avro


import org.apache.avro.Schema
import org.apache.avro.file.DataFileReader
import org.apache.avro.file.DataFileWriter
import org.apache.avro.file.SeekableByteArrayInput
import org.apache.avro.file.SeekableInput
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.generic.GenericDatumWriter
import org.apache.avro.io.DatumReader
import org.apache.avro.io.DatumWriter
import org.apache.avro.reflect.ReflectData
import org.apache.commons.io.FileUtils

class AvroTutorial {
    static main(args) {
        Object user = new User(
            username: "dean",
            age: 30,
            hasChildren: true,
            size: 100,
            ok: null,
            myBytes: null,
            childrens: [new Children(name: "haha")],
            birthday: new Date().getTime()
        )

        // Serialize
        def data = serialize(makeSchema(user), user)
        makeBatFile(data)

        // Deserialize
        def record = deserialize(getSchema(), getData())

        println("completed")
    }

    private static Schema makeSchema(Object obj) {
        def schema = ReflectData.AllowNull.get().getSchema(obj.class)

        def file = new File("/Users/dean/Downloads/schema.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        file.text = schema.toString(true)

        return schema
    }

    private static void makeBatFile(byte[] data) {
        def file = new File("/Users/dean/Downloads/avro.bat")
        if (!file.exists()) {
            file.createNewFile()
        }

        FileUtils.writeByteArrayToFile(file, data)
    }

    private static Schema getSchema() {
        def file = new File("/Users/dean/Downloads/schema.txt")
        def text = file.text
        return Schema.parse(text)
    }

    private static byte[] getData() {
        def file = new File("/Users/dean/Downloads/avro.bat")
        return file.bytes
    }

    private static byte[] serialize(Schema schema, Object obj) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream()
        DatumWriter datumWriter = new GenericDatumWriter(schema)
        DataFileWriter dataFileWriter = new DataFileWriter(datumWriter)
        dataFileWriter.create(schema, os)

        Object cObj = AvroUtil.convertRawObject(schema, obj)
        dataFileWriter.append(cObj)
        dataFileWriter.close()
        os.close()

        return os.toByteArray()
    }

    private static Object deserialize(Schema schema, byte[] data) throws IOException {
        DatumReader datumReader = new GenericDatumReader(schema)
        SeekableInput si = new SeekableByteArrayInput(data)
        DataFileReader dataFileReader = new DataFileReader(si, datumReader)
        Object record = null
        while (dataFileReader.hasNext()) {
            // Reuse user object by passing it to next(). This saves us from
            // allocating and garbage collecting many objects for files with
            // many items.
            record = dataFileReader.next(record)
//			System.out.println(record);
        }
        return record
    }
}
