package xstream

import xstream.annotation.CollectionElement
import xstream.annotation.ComplexElement
import xstream.annotation.Element
import xstream.annotation.ElementAlias
import xstream.annotation.GroupElement
import xstream.annotation.RootElement
import xstream.annotation.SimpleType

/**
 * Created by Dean on 2017/2/27.
 */

@RootElement
@ElementAlias(name="刑事一审案件")
class FirstCriminalCase {

    @Element(simpleType = SimpleType.NonNegativeInteger, minOccurs = 1)
    @ElementAlias(name="案件标识")
    Long caseNo

    @CollectionElement
    @ElementAlias(name="当事人")
    List<Party> parties

    @GroupElement
    @ElementAlias(name="办案期限")
    CaseDue caseDue
}
