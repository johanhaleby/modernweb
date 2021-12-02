package com.haleby.htmx.htmxexample.example5

import com.haleby.htmx.htmxexample.common.UserRepository
import kotlinx.html.*
import kotlinx.html.ButtonType.button
import kotlinx.html.stream.appendHTML
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = ["/examples/5"], produces = [MediaType.TEXT_HTML_VALUE])
@RestController
class Example5(private val userRepository: UserRepository) {

    @GetMapping
    fun start(): String = StringBuilder().appendHTML().html {
        head {
            title("Example 5")
            link(rel = "stylesheet", href = "/webjars/bootstrap/5.1.3/css/bootstrap.min.css")
            script(type = "text/javascript", src = "/webjars/bootstrap/5.1.3/js/bootstrap.min.js") {}
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
                table(classes = "table table-striped") {
                    thead {
                        tr {
                            th { +"Firstname" }
                            th { +"Lastname" }
                            th { +"Email" }
                        }
                    }
                    tbody {
                        repeat(10) {
                            val user = userRepository.random()
                            tr {
                                td { +user.firstName }
                                td { +user.lastName }
                                td { +user.email }
                            }
                        }
                    }
                }
            }
        }

    }.toString()
}