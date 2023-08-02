package com.skid.fitnesskittestapp.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.skid.fitnesskittestapp.data.model.LessonEntity
import com.skid.fitnesskittestapp.domain.model.LessonItem
import java.time.Duration
import java.time.LocalTime

fun FragmentActivity.addFragment(
    @IdRes containerViewId: Int,
    fragment: Fragment,
) {
    supportFragmentManager.beginTransaction().apply {
        add(containerViewId, fragment)
    }.commit()
}

fun LessonEntity.asLessonItem(coachName: String): LessonItem {
    val startLocalTime = LocalTime.parse(startTime)
    val endLocalTime = LocalTime.parse(endTime)
    val duration = Duration.between(startLocalTime, endLocalTime).toHoursAndMinutesString()

    return LessonItem(
        id = appointment_id,
        name = name,
        coachName = coachName,
        date = date,
        startTime = startTime,
        endTime = endTime,
        duration = duration,
        place = place,
        color = color.substring(1).toInt(16) or (0xFF shl 24)
    )
}

fun Duration.toHoursAndMinutesString(): String {
    val hours = this.toHours()
    val minutes = this.toMinutes() % 60
    return when {
        hours > 0 -> "${hours}ч. ${minutes}мин."
        else -> "${minutes}мин."
    }
}