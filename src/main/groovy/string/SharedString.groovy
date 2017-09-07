package string

class SharedString {
    static main(args) {
        def strItem = "yyyy"
//        def str1 = "xxxxx${strItem}zzzzz"
//        println(str1)

//        def str2 = 'xxxxxx'

//        def str3 = '''  SELECT XXX
//                        FROM YYY
//                        WHERE
//                            YYY.A = 1
//                        AND
//                            XXX
//                    '''
//        println(str3)

        def str4 = """ 
                       xxxxx
                       ${strItem}
                       zzzzz
                    """
        println(str4)
    }
}
