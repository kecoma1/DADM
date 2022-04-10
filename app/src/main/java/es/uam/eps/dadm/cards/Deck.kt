package es.uam.eps.dadm.cards

import java.util.*

class Deck(
    _name: String,
    var id: String = UUID.randomUUID().toString()
) {
    val cards = mutableListOf<Card>()
    var name : String = _name
    var nameReduced : String = ""
        get() = if (30 < name.length) name.substring(0, 27) + "..." else name
    var numCards : Int = cards.size
        get() = cards.size

    fun addCard() {

    }

    fun deleteCard() {

    }

    private fun listCardsWithIndex() {
        cards.withIndex().forEach { (i, card) ->
            println("${i+1}. ${card.question} -> ${card.answer}") }
    }
}