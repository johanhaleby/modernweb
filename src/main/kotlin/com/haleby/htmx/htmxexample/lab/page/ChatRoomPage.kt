package com.haleby.htmx.htmxexample.lab.page

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(path = ["/chat/room"], produces = [MediaType.TEXT_HTML_VALUE])
@Controller
class ChatRoomPage {
}