import org.junit.Test

import javax.script.Bindings
import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

/**
 * Created by Dean on 2017/6/7.
 */
class ScriptEngineTest {

    @Test
    void testGroovyEngine() {
        def script = """
                        class Greeter {
                            String sayHello() {
                                def greet = new Dependency().message
                                greet
                            }
                        }
                      """
        def engine = new GroovyScriptEngine()
    }

    @Test
    void testGroovyEngine2() {
        def script = '''def hello(language, count) {return "Hello $language"}'''
        GroovyShell shell = new GroovyShell()
        Script groovyScript = shell.parse(script)
        Object[] args = {}
        Object result = groovyScript.invokeMethod("hello", "Groovy", 2)
        assert result =="Hello Groovy"
    }

    @Test
    void testEngine2() {
        String HelloLanguage = '''def hello(language, count) {return "Hello $language $count"}'''
        ScriptEngineManager factory = new ScriptEngineManager()
        ScriptEngine engine = factory.getEngineByName("Groovy")
        engine.eval(HelloLanguage)
        Invocable inv = (Invocable) engine
        Object result = inv.invokeFunction("hello", "Groovy", 2)
        assert result =="Hello Groovy"
    }

    @Test
    void testEngine() {
        def script = '''
                        def execute(List list) {
                            return "result: ${list[0]}"
                        }
                     '''
        def engine = getEngine()
        if(engine){
            Bindings binding = engine.createBindings()
            engine.eval(script, binding)
            def result =  engine.invokeMethod("execute", "Dean")
            println(result)
        }
    }

    static ScriptEngine _engine = null
    static ScriptEngine getEngine(){
        if(!_engine){
            //创建engine实例直接解析文本内容
            ScriptEngineManager factory = new ScriptEngineManager()
            //每次生成一个engine实例
            _engine = factory.getEngineByName("groovy")
        }
        return _engine
    }
}
