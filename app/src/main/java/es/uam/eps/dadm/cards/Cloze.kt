package es.uam.eps.dadm.cards

class Cloze(
    question: String,
    answer: String,
    extraQuality: Boolean = false
) : Card(question, answer, extraQuality = extraQuality) {

    companion object {
        fun fromString(cad: String): Cloze = Card.fromString(cad) as Cloze
    }

    private val clozeAnswer = question.replace("\\*(.*?)\\*".toRegex(), answer)

    override fun show() {
        print("$question (ENTER to see the answer): ")
        val input = readLine() ?: ""
        println("Answer: $clozeAnswer")

        updateStats(input)
    }

    override fun toString(): String = super.toString().replaceBefore('|', " cloze ")
    override fun summary(): String = super.summary().replaceBefore(')', "(Cloze")

}