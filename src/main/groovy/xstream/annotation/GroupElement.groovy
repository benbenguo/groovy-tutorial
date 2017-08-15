package xstream.annotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Created by Dean on 2017/2/28.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.FIELD])
@interface GroupElement {
    int minOccurs() default 0
    int maxOccurs() default 1
}