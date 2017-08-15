package kit.utils

/**
 * Created by Dean on 2017/1/14.
 *
 * The Expando class can be used to create a dynamically expandable object.
 * Despite its name it does not use the ExpandoMetaClass underneath.
 * Each Expando object represents a standalone,
 * dynamically-crafted instance that can be extended with properties (or methods) at runtime.
 */
class ExpandoTutorial {
    static main(args) {

        def finalV = 2

        def expando = new Expando()
        expando.name = 'John'
        expando.toString = { -> 'John' }
        expando.say = { String s, Integer i ->
            def rst1 = "John says: ${s}, "
            def rst2 = "++i: ${++i + finalV}"
            def result = rst1 + rst2
            return result
        }

        assert expando.name == 'John'
        assert expando as String == 'John'
        assert expando.say('Hi', 1) == 'John says: Hi, ++i: 4'

        // inline method
        def say = { String s, Integer i ->
            def rst1 = "John says: ${s}, "
            def rst2 = "++i: ${++i + finalV}"
            def result = rst1 + rst2
            return result
        }

        assert say('Hi', 1) == 'John says: Hi, ++i: 4'
    }
}
