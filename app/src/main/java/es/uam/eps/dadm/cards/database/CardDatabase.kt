package es.uam.eps.dadm.cards.database

import androidx.room.Database
import androidx.room.RoomDatabase
import es.uam.eps.dadm.cards.Card

@Database(entities = [Card::class], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {
    abstract val cardDao: CardDao
}