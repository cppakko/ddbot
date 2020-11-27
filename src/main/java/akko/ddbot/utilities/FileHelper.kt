package akko.ddbot.utilities

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel

@Throws(IOException::class)
fun copyFile(source: File, dest: File) {
    var inputChannel: FileChannel? = null
    var outputChannel: FileChannel? = null
    try {
        inputChannel = FileInputStream(source).channel
        outputChannel = FileOutputStream(dest).channel
        outputChannel.transferFrom(inputChannel, 0, inputChannel.size())
    } finally {
        inputChannel!!.close()
        outputChannel!!.close()
    }
}