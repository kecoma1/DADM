package es.uam.eps.dadm.cards

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "decks_table")
class Deck(
    var id: String = UUID.randomUUID().toString(),
    @PrimaryKey val deckId: Long,
    var name: String
) {
    init { lastDeckId = deckId }

    /*
    *
    * ATTENTION:
    * The code analyzer says that the next properties could be val instead of var. If we make
    * this change, we have a big mistake because these values are going to be changed during
    * the execution of the application.
    */
    var nameReduced : String = ""
        get() = if (30 < name.length) name.substring(0, 27) + "..." else name

    companion object {
        var lastDeckId : Long = 0
    }
}