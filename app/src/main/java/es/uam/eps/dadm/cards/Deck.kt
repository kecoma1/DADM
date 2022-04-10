package es.uam.eps.dadm.cards

import java.util.*

class Deck(
    _name: String,
    var id: String = UUID.randomUUID().toString()
) {
    val cards = mutableListOf<Card>()
    var name : String = _name

    /*
    *
    * ATTENTION:
    * The code analyzer says that the next properties could be val instead of var. If we make
    * this change, we have a big mistake because these values are going to be changed during
    * the execution of the application.
    */
    var nameReduced : String = ""
        get() = if (30 < name.length) name.substring(0, 27) + "..." else name
}