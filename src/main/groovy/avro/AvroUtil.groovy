package avro

import org.apache.avro.Schema
import org.apache.avro.generic.GenericData

import java.nio.ByteBuffer

import static org.apache.avro.Schema.Type.*

class AvroUtil {

    /**
     *
     * @param rSchema
     * @param obj
     * @return
     */
    static Object convertRawObject(Schema rSchema, Object obj) {
        if (obj != null) {
            Schema schema = rSchema
            if (rSchema.type == UNION) {
                schema = rSchema.types.find {
                    it.type != NULL
                }
            }

            if (schema.type == ARRAY) {
                def result = []
                if (schema.elementType.type != NULL) {
                    obj.each { item ->
                        result.add(convertRawObject(schema.elementType, item))
                    }
                }
                return result
            } else if (schema.type == MAP) {
                def result = [:]
                if (schema.valueType.type != NULL) {
                    obj.each { key, value ->
                        result.put(key, convertRawObject(schema.valueType, value))
                    }
                }
                return result
            } else if (schema.type == RECORD) {
                GenericData.Record record = new GenericData.Record(schema)
                schema.fields.each { sField ->
                    record.put(sField.name(), convertRawObject(sField.schema(), obj[sField.name()]))
                }
                return record
            } else {
                if (obj instanceof byte[]) {
                    return ByteBuffer.wrap(obj)
                }
                return obj
            }
        }
        return obj
    }
}
