package akko.ddbot.command

import cc.moecraft.icq.command.CommandProperties
import cc.moecraft.icq.command.interfaces.GroupCommand
import cc.moecraft.icq.event.events.message.EventGroupMessage
import cc.moecraft.icq.sender.message.MessageBuilder
import cc.moecraft.icq.sender.message.components.ComponentImage
import cc.moecraft.icq.user.Group
import cc.moecraft.icq.user.GroupUser
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.awt.image.ImageObserver
import java.io.File
import java.io.FileOutputStream
import java.util.ArrayList
import javax.imageio.ImageIO

class WxImgCommand : GroupCommand {
    override fun properties(): CommandProperties {
        return CommandProperties("wx")
    }

    override fun groupMessage(
        p0: EventGroupMessage?,
        p1: GroupUser?,
        p2: Group?,
        p3: String?,
        p4: ArrayList<String>?
    ): String {
        p0!!.delete()
        if (p4 != null)
        {
            if (p4.size > 0)
            {
                val str = p0.rawMessage
                drawWX(str.split(" ", ignoreCase = true, limit = 2)[1])
            }
            else drawWX("")
        }
        else drawWX("")
        return MessageBuilder().add(ComponentImage("WX_test.jpg")).toString()
    }
}

private fun drawWX(str:String)
{
    val loop = str.length

    val imgHeight = 80
    var minImgWidth = 136
    val id = "【懂王】人下人"
    val iconX = 10
    val iconY = 10
    var leftX = 40
    val leftY = 25
    val imageUpdate = ImageObserver { _, _, _, _, _, _ -> true }

    if (loop > 5) minImgWidth += (loop - 5) * 12

    val bufferedImage = BufferedImage(minImgWidth,imgHeight, BufferedImage.TYPE_INT_RGB)
    val graphics = bufferedImage.graphics as (Graphics2D)
    graphics.run {
        setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        color = Color.getHSBColor(0F,0F,0.95F)
        fillRect(0,0,minImgWidth,imgHeight)
        drawImage(ImageIO.read(File("img/icon.png")),iconX,iconY, imageUpdate)
        color = Color.GRAY
        font = Font("YaHei-Consolas-Hybrid", Font.BOLD , 10)
        drawString(id,45,20)
        drawImage(ImageIO.read(File("img/left.png")),leftX,leftY, imageUpdate)
        leftX += 4
        for (i in 0 until loop)
        {
            val char = str[i].toInt()
            if (char in 32..126)
            {
                leftX += 7
            }
            else leftX += 12
            drawImage(ImageIO.read(File("img/mid.png")),leftX,leftY, imageUpdate)
        }
        leftX += 14
        drawImage(ImageIO.read(File("img/right.png")),leftX,leftY, imageUpdate)
        color = Color.BLACK
        font = Font("YaHei-Consolas-Hybrid", Font.TRUETYPE_FONT , 12)
        drawString(str,58,48)
    }
    ImageIO.write(bufferedImage,"JPEG", FileOutputStream("/home/ProjectBot/data/images/WX_test.jpg"))
}