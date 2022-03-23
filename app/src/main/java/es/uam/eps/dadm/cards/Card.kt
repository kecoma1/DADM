package es.uam.eps.dadm.cards

import android.view.View
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.max
import kotlin.math.roundToLong

open class Card(
    var question: String,
    var answer: String,
    var date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString(),
    var id: String = UUID.randomUUID().toString(),
    var extraQuality: Boolean = false
) {
    private var quality: Int? = null
    private val qualityOpt = mutableListOf(0, 3, 5)

    var answered = false

    // For the SM2 algorithm
    var repetitions: Int = 0
    var interval: Long = 1L
    var nextPracticeDate = date
    var easiness: Double = 2.5

    // For statistics
    var timesDone: Int = 0
    var sucesses: Int = 0

    init { if (extraQuality) qualityOpt.apply { add(1); add(4) } }

    fun updateFromView(view: View) {
        quality = when(view.id) {
            R.id.easy_button -> 5
            R.id.medium_button -> 3
            R.id.hard_button -> 0
            else -> throw Exception("Unavailable quality")
        }
        update(LocalDateTime.now())
    }

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
            " $sucesses "

    companion object {
        fun fromString(cad: String): Card {
            // Removing the first and last space
            val parts = cad.split("|").map { it.substring(1, it.length-1) }

            val c: Card = when {
                parts[0].contains("cloze") -> Cloze(parts[1], parts[2])
                parts[0].contains("card") -> Card(parts[1], parts[2])
                parts[0].contains("sinonim") -> Sinonim(parts[1], parts[2])
                else -> throw Exception("Not a card nor a cloze nor a sinonim")
            }

            c.date = parts[3]
            c.id = parts[4]
            c.easiness = parts[5].toDouble()
            c.repetitions = parts[6].toInt()
            c.interval = parts[7].toLong()
            c.nextPracticeDate = parts[8]
            c.timesDone = parts[9].toInt()
            c.sucesses = parts[10].toInt()
            return c
        }
    }

    open fun show() {
        print("$question (ENTER to see the answer): ")
        val input = readLine() ?: ""
        println("Answer: $answer")

        updateStats(input)
    }

    fun updateStats(input: String) {
        timesDone++
        if (input == answer) sucesses++
    }

    private fun askQuality() {
        // Asking the quality
        do {
            print("How was the question?:\n" +
                    "\t0 -> Difficult\n" +
                    (if (extraQuality) "\t1 -> Difficult, but not much\n" else "") +
                    "\t3 -> Doubt\n" +
                    (if (extraQuality) "\t4 -> Easy, but not much\n" else "") +
                    "\t5 -> Easy\n" +
                    if (extraQuality) "Type 0, 1, 3, 4 or 5: " else "Type 0, 3 or 5: " )
            quality = readLine()?.toIntOrNull()
        } while(quality == null || !qualityOpt.contains(quality))
    }

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

    fun simulate(period: Long) {
        println("Simulation of the card $question:")
        var now = LocalDateTime.now()

        val f = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        var intervalStr = ""

        var counter = 0
        do {
            val nowStr = now.format(f)
            println(nowStr)

            intervalStr += if (nowStr == nextPracticeDate) {
                show()
                update(now)
                details()
                "*\t"
            } else "\t"

            now = now.plusDays(1)
            counter += 1
        } while(counter <= period)

        for(i in 1..period) print("$i\t")
        println("\n$intervalStr")
    }

    private fun details() = println(String.format("Easiness: %.2f - " +
            "Repetitions: $repetitions - " +
            "Interval: $interval - " +
            "Next date: $nextPracticeDate - " +
            "Times answered: $timesDone - " +
            "Right answers: $sucesses", easiness))

    open fun summary() = "(Card)\t$question -> $answer " +
            "- Times done: $timesDone " +
            "- Successes: ${"%.2f".format(
                if (timesDone > 0) ((sucesses*100)/timesDone).toDouble()
                else 0.0)
            }% ($sucesses)"

}