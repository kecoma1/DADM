package es.uam.eps.dadm.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import es.uam.eps.dadm.cards.database.CardDatabase

class CardListViewModel(application: Application)
    : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val cards: LiveData<List<Card>> = CardDatabase.getInstance(context).cardDao.getCards()

    fun deckCards(id: Long): LiveData<List<Card>> = Transformations.map(cards) {
        it.filter { c -> c.deckId == id }
    }
}