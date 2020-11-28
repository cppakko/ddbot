package akko.ddbot.utilities

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hydev.logger.HyLogger

class GlobalObject {
    companion object {
        val objectMapper = ObjectMapper()
        val jacksonObjectMapper = jacksonObjectMapper()
        val log = HyLogger("common")
    }
}