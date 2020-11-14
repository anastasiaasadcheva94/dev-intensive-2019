package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

fun User.toUserView(): UserView {
    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status = if (lastVisit == null) "Ещё ни разу не был" else if(isOnline) "online" else "Последний раз был ${lastVisit!!.humanizeDiff()}"

    return UserView(
        id,
        fullName = "$firstName $lastName" ,
        nickName = nickName,
        initials = initials,
        status = status,
        avatar = avatar)
}



fun Date.humanizeDiff(date: Date = Date()): String {

    return when (val diff = date.time - this.time) {
        in TimeUnits.SECOND.toMillis(0)..TimeUnits.SECOND.toMillis(1) -> "только что"
        in TimeUnits.SECOND.toMillis(1)..TimeUnits.SECOND.toMillis(45) -> "несколько секунд назад"
        in TimeUnits.SECOND.toMillis(45)..TimeUnits.SECOND.toMillis(75) -> "минуту назад"
        in TimeUnits.SECOND.toMillis(75)..TimeUnits.MINUTE.toMillis(45) -> TimeUnits.MINUTE.getDeclentedRepresentation(diff)
        in TimeUnits.MINUTE.toMillis(45)..TimeUnits.MINUTE.toMillis(75) -> "час назад"
        in TimeUnits.MINUTE.toMillis(75)..TimeUnits.HOUR.toMillis(22) -> TimeUnits.HOUR.getDeclentedRepresentation(diff)
        in TimeUnits.HOUR.toMillis(22)..TimeUnits.HOUR.toMillis(26) -> "день назад"
        in TimeUnits.HOUR.toMillis(26)..TimeUnits.DAY.toMillis(360) -> TimeUnits.DAY.getDeclentedRepresentation(diff)
        else -> "более года назад"
    }

}