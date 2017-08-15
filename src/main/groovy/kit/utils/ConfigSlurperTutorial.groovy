package kit.utils

/**
 * Created by Dean on 2017/1/14.
 *
 * ConfigSlurper is a utility class for reading configuration files defined in the form of Groovy scripts.
 * Like it is the case with Java *.properties files, ConfigSlurper allows a dot notation.
 * But in addition, it allows for Closure scoped configuration values and arbitrary object types.
 */
class ConfigSlurperTutorial {
    static main(args) {
        GroovyClassLoader classLoader = new GroovyClassLoader()
        def config = new ConfigSlurper().parse(classLoader.getResource("Config.groovy"))

        assert config.app.name == 'tutorial'
        assert config.app.date instanceof Date
        assert config.app.age == 42
        assert config.app.text == 'Test 42'
        assert config.app.nullText != null
        assert config.app.nullText.size() == 0

        config = new ConfigSlurper('development').parse(classLoader.getResource("Config.groovy"))
        assert config.app.port == 8080
    }
}
