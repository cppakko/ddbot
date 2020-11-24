package akko.ddbot.utilities

import com.fasterxml.jackson.databind.ObjectMapper

class GlobalObject {
    companion object {
        val objectMapper = ObjectMapper()
    }
}