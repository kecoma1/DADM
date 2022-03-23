package es.uam.eps.dadm.cards

class Sinonim(
    question: String,
    answer: String,
    extraQuality: Boolean = false
) : Card(question, answer, extraQuality = extraQuality) {

    companion object {
        fun fromString(cad: String): Sinonim = Card.fromString(cad) as Sinonim
    }

    override fun show() {
        print("$question (ENTER to see the sinonim): ")
        val input = readLine() ?: ""
        println("Sinonim: $answer")

        updateStats(input)
    }

    override fun toString(): String = super.toString().replaceBefore("|"," sinonim ")
    override fun summary(): String = super.summary().replaceBefore(')', "(Sinonim")
}