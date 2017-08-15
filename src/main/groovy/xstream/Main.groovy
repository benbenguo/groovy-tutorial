package xstream

/**
 * Created by Dean on 2017/2/27.
 */
class Main {

    static main(arg) {
        XmlBuilder xmlBuilder = new XmlBuilder()
        def caseDue = new CaseDue(start: new Date(), days: 10)
        def list = [new Party(userName: "Dean"), new Party(userName: "Alice")]
        def firstCriminalCase = new FirstCriminalCase(caseNo: 12345678l, parties: list, caseDue: caseDue)

        xmlBuilder.builder(firstCriminalCase)
    }
}
