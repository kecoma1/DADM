package es.uam.eps.dadm.cards

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "decks_table")
class Deck(
    var id: String = UUID.randomUUID().toString(),
    @PrimaryKey val deckId: Long,
    var name: String,
    var userId: String = Firebase.auth.currentUser!!.uid
) {
    init { lastDeckId = deckId }

    constructor() : this(
        UUID.randomUUID().toString(),
        lastDeckId,
        "deck",
    )

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

        fun fromFirebaseToDecks(snapshot: DataSnapshot): List<Deck> {
            val listOfDecks = mutableListOf<Deck>()
            for (child in snapshot.children)
                child.getValue(Deck::class.java)?.let { listOfDecks.add(it) }

            return listOfDecks.filter { it.userId == Firebase.auth.currentUser!!.uid }
        }
    }
}