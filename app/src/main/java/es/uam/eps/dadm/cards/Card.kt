package es.uam.eps.dadm.cards

import androidx.room.ColumnInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.max
import kotlin.math.roundToLong
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards_table")
open class Card(
    @ColumnInfo(name="card_question")
    var question: String,
    var answer: String,
    var date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString(),
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
) {
    private var quality: Int? = null

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

    /*
    * ATTENTION:
    * The code analyzer says that the next properties could be val instead of var. If we make
    * this change, we have a big mistake because these values are going to be changed during
    * the execution of the application.
    */
    // For displaying
    var answerReduced: String = ""
        get() = if (12 < answer.length) answer.substring(0, 11) + "..."
                else answer
    var answerLongReduced: String = ""
        get() = if (25 < answer.length) answer.substring(0, 22) + "..."
                else answer
    var questionReduced: String = ""
        get() = if (12 < question.length) question.substring(0, 11) + "..."
                else question
    var questionLongReduced: String = ""
        get() = if (25 < question.length) question.substring(0, 22) + "..."
                else question
    var easinessReduced: String = String.format("%.2f", easiness)
        get() = String.format("%.2f", easiness)

    // Flag to check whether or not the details of a card are hidden.
    var detailsHidden: Boolean = true
    var help: String = ""

    init {
        if (answer.length in 2..6) help = answer.substring(0, 1)
        else if (answer.length > 7)
            for (c in answer)
                if ((0 until 10).random() < 5) // 50% chance of showing a character
                    help += c
                else help += " "
    }

    fun isDue(): Boolean { return isDue(LocalDateTime.now()) }
    private fun isDue(date: LocalDateTime): Boolean {
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