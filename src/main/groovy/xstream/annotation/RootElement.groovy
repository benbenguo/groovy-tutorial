package xstream.annotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Created by Dean on 2017/2/27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE])
@interface RootElement {

}