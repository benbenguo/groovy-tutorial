package xstream

import xstream.annotation.Element
import xstream.annotation.ElementAlias
import xstream.annotation.RootElement
import xstream.annotation.SimpleType

/**
 * Created by Dean on 2017/2/28.
 */
@RootElement
@ElementAlias(name="当事人")
class Party {

    @Element(simpleType = SimpleType.C10)
    @ElementAlias(name="当事人姓名")
    String userName
}
