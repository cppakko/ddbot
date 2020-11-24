package akko.ddbot.utilities

import akko.ddbot.BotMainActivity
import cc.moecraft.icq.sender.returndata.ReturnStatus

fun GroupMsg(group_id: Long,msg: String): ReturnStatus? { return BotMainActivity.bot!!.accountManager.nonAccountSpecifiedApi.sendGroupMsg(group_id,msg).status }