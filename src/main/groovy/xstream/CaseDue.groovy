package xstream

import xstream.annotation.Element
import xstream.annotation.ElementAlias
import xstream.annotation.RootElement
import xstream.annotation.SimpleType

/**
 * Created by Dean on 2017/2/28.
 */
@ElementAlias(name = "办案期限")
@RootElement
class CaseDue {

    @ElementAlias(name = "审限起始日期")
    @Element(simpleType = SimpleType.D)
    Date start

    @ElementAlias(name = "审限起始日期")
    @Element(simpleType = SimpleType.SN)
    Short days
}
