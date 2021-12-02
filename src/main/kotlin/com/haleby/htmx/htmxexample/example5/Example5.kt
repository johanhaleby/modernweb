package com.haleby.htmx.htmxexample.example5

import com.haleby.htmx.htmxexample.common.UserRepository
import kotlinx.html.*
import kotlinx.html.ButtonType.button
import kotlinx.html.InputType.text
import kotlinx.html.stream.appendHTML
import org.intellij.lang.annotations.Language
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct

@RequestMapping(path = ["/examples/5"], produces = [MediaType.TEXT_HTML_VALUE])
@RestController
class Example5(private val userRepository: UserRepository) {

    @GetMapping
    fun start(): String = StringBuilder().appendHTML().html {
        head {
            title("Example 5")
            link(rel = "stylesheet", href = "/webjars/bootstrap/css/bootstrap.min.css")
            script(type = "text/javascript", src = "/webjars/bootstrap/js/bootstrap.min.js") {}
            script(type = "text/javascript", src = "/webjars/htmx.org/dist/htmx.min.js") {}
            script(type = "text/javascript", src = "/webjars/hyperscript.org/dist/_hyperscript.js") {}
        }
        body {
            div(classes = "container") {
                div(classes = "alert alert-warning alert-dismissible fade show") {
                    role = "alert"
                    button(type = button, classes = "btn-close") {
                        attributes["data-bs-dismiss"] = "alert"
                        attributes["aria-label"] = "Close"
                    }
                    strong { +"Success!" }
                    +" Example 5 is totally working"
                }

                unsafe {
                    raw("<div>You can use raw html as well!</div>")
                }
                br
                h2 { +"Users" }
                h3 {
                    +"Search"
                    span(classes = "htmx-indicator") {
                        img(src = "/img/bars.svg") { +"Searching..." }
                    }
                }
                input(classes = "form-control", type = text, name = "search") {
                    placeholder = "Begin Typing To Search Users..."
                    attributes["hx-post"] = "/examples/5/search"
                    attributes["hx-trigger"] = "keyup changed delay:500ms"
                    attributes["hx-target"] = "#search-results"
                    attributes["hx-indicator"] = ".htmx-indicator"

                }

                table(classes = "table table-striped") {
                    thead {
                        tr {
                            th { +"Firstname" }
                            th { +"Lastname" }
                            th { +"Email" }
                        }
                    }
                    tbody {
                        id = "search-results"
                        unsafe {
                            raw(search(""))
                        }
                    }
                }
            }
        }
    }.toString()

    @PostMapping("/search")
    fun search(@RequestParam("search") searchString: String): String {
        val normalizedSearchString = searchString.lowercase()
        return userRepository.findAll()
                .filter { (_, firstName, lastName, email) ->
                    firstName.lowercase().contains(normalizedSearchString) || lastName.lowercase().contains(normalizedSearchString) || email.lowercase().contains(normalizedSearchString)
                }
                .joinToString("\n") { (_, firstName, lastName, email) ->
                    "<tr><td>$firstName</td><td>$lastName</td><td>$email</td></tr>"
                }
    }
}