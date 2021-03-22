package akko.ddbot.utilities

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class GlobalObject {
    companion object {
        val objectMapper = ObjectMapper()
        val jacksonObjectMapper = jacksonObjectMapper()
    }
}