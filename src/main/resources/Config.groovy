/**
 * Created by Dean on 2017/1/14.
 */
app {
    name = "tutorial"
    date = new Date()
    age  = 42
    text = "Test ${age}"
}

environments {
    development {
        app.port = 8080
    }

    test {
        app.port = 8082
    }

    production {
        app.port = 80
    }
}