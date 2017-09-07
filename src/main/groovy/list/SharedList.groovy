package list

class SharedList {
    static main(args) {
        listIndex()
    }

    static listIndex() {
        def list = [1, 2, 3, 4, 5]
        println(list[-2..-1])
        def olist = [3, 4, 5]
        println(list - olist)


    }

    static def groupedList() {
        def list = []

        list.add([
                success: true,
                attachmentId: 11,
                type: "PNG",
                name: "com_android_systemui_tests_96x96-51475.png",
                tmpPath: "http://onsonce.imwork.net/original/driver/2017-08/194/com_android_systemui_tests_96x96-150337079000656.png",
                fileSize: 7499.0,
                dateCreated: "2017-08-22T02:59:50Z",
                typeName: "车身"
        ])

        list.add([
                success: true,
                attachmentId: 11,
                type: "PNG",
                name: "com_android_systemui_tests_96x96-51475.png",
                tmpPath: "http://onsonce.imwork.net/original/driver/2017-08/194/com_android_systemui_tests_96x96-150337079000656.png",
                fileSize: 7499.0,
                dateCreated: "2017-08-22T02:59:50Z",
                typeName: "车头"
        ])

        list.add([
                success: true,
                attachmentId: 11,
                type: "PNG",
                name: "com_android_systemui_tests_96x96-51475.png",
                tmpPath: "http://onsonce.imwork.net/original/driver/2017-08/194/com_android_systemui_tests_96x96-150337079000656.png",
                fileSize: 7499.0,
                dateCreated: "2017-08-22T02:59:50Z",
                typeName: "车头"
        ])

        list.add([
                success: true,
                attachmentId: 11,
                type: "PNG",
                name: "com_android_systemui_tests_96x96-51475.png",
                tmpPath: "http://onsonce.imwork.net/original/driver/2017-08/194/com_android_systemui_tests_96x96-150337079000656.png",
                fileSize: 7499.0,
                dateCreated: "2017-08-22T02:59:50Z",
                typeName: "车胎"
        ])

        list.add([
                success: true,
                attachmentId: 11,
                type: "PNG",
                name: "com_android_systemui_tests_96x96-51475.png",
                tmpPath: "http://onsonce.imwork.net/original/driver/2017-08/194/com_android_systemui_tests_96x96-150337079000656.png",
                fileSize: 7499.0,
                dateCreated: "2017-08-22T02:59:50Z",
                typeName: "车胎"
        ])

        def result = list.groupBy {
            it.typeName
        }.collect {[
                typeName: it.key,
                fileList: it.value
        ]}

        return result
    }
}
