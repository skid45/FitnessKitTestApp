package com.skid.fitnesskittestapp.domain.usecases

import com.skid.fitnesskittestapp.domain.model.HeaderItem
import com.skid.fitnesskittestapp.domain.model.LessonItem
import com.skid.fitnesskittestapp.domain.model.ScheduleListItem
import com.skid.fitnesskittestapp.domain.repositories.ScheduleRepository
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class GetSortedScheduleUseCase(private val scheduleRepository: ScheduleRepository) {

    suspend operator fun invoke(): Result<List<ScheduleListItem>> {
        val result = scheduleRepository.getSchedule()
        return if (result.isSuccess) {
            val sortedLessonList = sortedLessonList(result.getOrNull()!!)
            val schedule = insertedHeadersSchedule(sortedLessonList)
            Result.success(schedule)
        } else result
    }

    private fun sortedLessonList(lessonList: List<LessonItem>): List<LessonItem> {
        return lessonList.sortedWith(compareBy({ it.date }, { LocalTime.parse(it.startTime) }))
    }

    private fun insertedHeadersSchedule(sortedLessonList: List<LessonItem>): List<ScheduleListItem> {
        val schedule = mutableListOf<ScheduleListItem>()
        var currentHeader: String? = null
        for (lesson in sortedLessonList) {
            val header = lesson.date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM"))
            if (header != currentHeader) {
                schedule.add(HeaderItem(header))
                currentHeader = header
            }
            schedule.add(lesson)
        }
        return schedule
    }
}