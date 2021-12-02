package com.haleby.htmx.htmxexample.example1

import org.intellij.lang.annotations.Language
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Show a simple page with Kotlin
 */
@RequestMapping(path = ["/examples/1"], produces = [MediaType.TEXT_HTML_VALUE])
@RestController
class Example1 {

    @GetMapping
    @Language("html")
    fun simplePage(): String = """
            <html lang="en">
                 <head>
                      <script src="/webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
                      <title>Example 1</title>
                      <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" />
                  </head>
                 <body>
                     <div class="container"><br/>
                         <div class="alert alert-warning alert-dismissible fade show" role="alert">
                           <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                           <strong>Success!</strong> Example 1 is totally working.
                         </div>
                     </div>
                 </body>
            </html>
        """.trimIndent()
}