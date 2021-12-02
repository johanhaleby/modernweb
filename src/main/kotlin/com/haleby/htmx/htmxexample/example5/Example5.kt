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
// Uses kotlinx-html-jvm (https://github.com/Kotlin/kotlinx.html)
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
                div(classes = "row") {
                    div(classes = "col-md-11") {
                        input(classes = "form-control", type = text, name = "search") {
                            placeholder = "Begin Typing To Search Users..."
                            attributes["hx-post"] = "/examples/5/search"
                            attributes["hx-trigger"] = "keyup changed delay:200ms"
                            attributes["hx-target"] = "#search-results"
                            attributes["hx-indicator"] = ".htmx-indicator"
                        }
                    }
                    div(classes = "col-md-1") {
                        div(classes = "spinner-border htmx-indicator") {
                            role = "status"
                            span(classes = "visually-hidden") {
                                +"Loading..."
                            }
                        }
                    }
                }

                div(classes = "row") {
                    div(classes = "col-md-12") {
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
            }
        }
    }.toString()

    @PostMapping("/search")
    fun search(@RequestParam("search") searchString: String): String =
            userRepository.findAll()
                    .filter { (_, firstName, lastName, email) ->
                        sequenceOf(firstName, lastName, email).any { it.contains(searchString, ignoreCase = true) }
                    }
                    .joinToString("\n") { (_, firstName, lastName, email) ->
                        "<tr><td>$firstName</td><td>$lastName</td><td>$email</td></tr>"
                    }
}