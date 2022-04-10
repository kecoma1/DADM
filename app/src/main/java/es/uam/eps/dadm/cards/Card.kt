package es.uam.eps.dadm.cards

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.max
import kotlin.math.roundToLong

open class Card(
    _question: String,
    _answer: String,
    private var date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString(),
    var id: String = UUID.randomUUID().toString()
) {
    private var quality: Int? = null
    private val qualityOpt = mutableListOf(0, 3, 5)

    var answered = false

    // For the SM2 algorithm
    var repetitions: Int = 0
    private var interval: Long = 1L
    var nextPracticeDate = date
    private var easiness: Double = 2.5
        set(value) {
            easinessReduced = String.format("%.2f", value)
            field = value
        }

    // For statistics
    private var timesDone: Int = 0
    private var successes: Int = 0

    // For displaying
    var answerReduced: String = ""
        get() = if (12 < answer.length) answer.substring(0, 11) + "..."
                else answer
    var answerLongReduced: String = ""
        get() = if (30 < answer.length) answer.substring(0, 27) + "..."
                else answer
    var questionReduced: String = ""
        get() = if (12 < question.length) question.substring(0, 11) + "..."
                else question
    var questionLongReduced: String = ""
        get() = if (30 < question.length) question.substring(0, 27) + "..."
                else question
    var easinessReduced: String = String.format("%.2f", easiness)
        get() = String.format("%.2f", easiness)
    var detailsHidden: Boolean = true

    var answer: String = _answer
    var question: String = _question

    fun isDue(): Boolean { return isDue(LocalDateTime.now()) }

    fun isDue(date: LocalDateTime): Boolean {
        val cardDate = LocalDate.parse(this.nextPracticeDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay()
        return cardDate.isBefore(date) || cardDate.isEqual(date)
    }

    override fun toString(): String = " card |" +
            " $question |" +
            " $answer |" +
            " $date |" +
            " $id |" +
            " $easiness |" +
            " $repetitions |" +
            " $interval |" +
            " $nextPracticeDate |" +
            " $timesDone |" +
            " $successes "

    fun updateCard(quality: Int) {
        this.quality = quality
        update(LocalDateTime.now())
    }

    private fun update(currentDate: LocalDateTime) {
        updateInfo(currentDate)
    }

    private fun updateInfo(currentDate: LocalDateTime) {
        // Computing the easiness
        easiness = max(1.3, easiness+0.1 - (5-quality!!) * (0.08+(5-quality!!)*0.02))

        // Computing the repetitions
        repetitions = if (quality!! < 3) 0 else repetitions+1

        // Computing the interval
        interval = when {
            repetitions <= 1 -> 1
            repetitions == 2 -> 6

            // repetitions > 2
            else -> (interval*easiness).roundToLong()
        }

        // Update next time
        val f = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        nextPracticeDate = currentDate.plusDays(interval).format(f)
    }

}