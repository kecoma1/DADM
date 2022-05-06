package es.uam.eps.dadm.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import es.uam.eps.dadm.cards.database.CardDatabase

class StatisticViewModel(application: Application)
    : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val cards: LiveData<List<Card>> = CardDatabase.getInstance(context).cardDao.getCards()

    val numberCards: LiveData<Int> = Transformations.map(cards) { it.size }

    val decks: LiveData<List<DeckWithCards>> =
        CardDatabase.getInstance(context).cardDao.getDecksWithCards()

    private val deckSelected = MutableLiveData<Long>()

    val deckWithCards: LiveData<List<DeckWithCards>> = Transformations.switchMap(deckSelected) {
        CardDatabase.getInstance(context).cardDao.getDeckWithCards(it)
    }

    fun loadDeckId(id: Long) {
        deckSelected.value = id
    }
}