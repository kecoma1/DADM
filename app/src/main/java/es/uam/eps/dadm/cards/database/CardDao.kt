package es.uam.eps.dadm.cards.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import es.uam.eps.dadm.cards.Card

@Dao
interface CardDao {

    companion object {
        @Volatile
        private var INSTANCE: CardDatabase? = null

        fun getInstance(context: Context): CardDatabase {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardDatabase::class.java,
                    "cards_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }

    @Query("SELECT * FROM cards_table")
    fun getCards(): List<Card>

    @Query("SELECT * FROM cards_table WHERE id = :id")
    fun getCard(id: String): Card?

    @Insert
    fun addCard(card: Card)
}