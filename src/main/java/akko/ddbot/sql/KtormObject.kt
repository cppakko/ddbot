package akko.ddbot.sql

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

class KtormObject {
    object VliverInfo : Table<Nothing>("vliver",schema = "groupinfo")
    {
        val name = varchar("vNAME")
        val pid = varchar("vID")
        val liveState = int("vSTATE")
        val id = int("id").primaryKey()
    }

    object FollowTable : Table<Nothing>("follow_info",schema = "groupinfo")
    {
        val userId = int("user_id").primaryKey()
        var id = int("vid")
    }
}