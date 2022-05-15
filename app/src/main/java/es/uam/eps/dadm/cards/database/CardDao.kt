package es.uam.eps.dadm.cards.database

import androidx.lifecycle.LiveData
import androidx.room.*
import es.uam.eps.dadm.cards.Card
import es.uam.eps.dadm.cards.Deck
import es.uam.eps.dadm.cards.DeckWithCards

@Dao
interface CardDao {

    @Query("SELECT * FROM cards_table WHERE userId = :userId")
    fun getCards(userId: String): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table WHERE userId = :userId LIMIT :n")
    fun getNCards(userId: String, n: Int): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table WHERE id = :id")
    fun getCard(id: String): LiveData<Card?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(card: Card)

    @Update
    fun updateCard(card: Card)

    @Delete
    fun deleteCard(card: Card)

    @Query("SELECT * FROM decks_table WHERE userId = :userId")
    fun getDecks(userId: String): LiveData<List<Deck>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: Deck)

    @Update
    fun updateDeck(deck: Deck)

    @Delete
    fun deleteDeck(deck: Deck)

    @Query("DELETE FROM cards_table WHERE deckId = :id")
    fun deleteDeckCards(id: Long)

    @Transaction
    @Query("SELECT * FROM decks_table")
    fun getDecksWithCards(): LiveData<List<DeckWithCards>>

    @Transaction
    @Query("SELECT * FROM decks_table WHERE deckId = :id")
    fun getDeckWithCards(id: Long): LiveData<List<DeckWithCards>>
}