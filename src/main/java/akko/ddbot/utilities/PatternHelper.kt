package akko.ddbot.utilities

import java.util.regex.Matcher
import java.util.regex.Pattern

class PatternHelper {
    fun regexHelper(regex: String,text: String):Matcher
    {
        val patterN = Pattern.compile(regex)
        val matcher = patterN.matcher(text)
        val isFind = matcher.find()
        return matcher
    }
}