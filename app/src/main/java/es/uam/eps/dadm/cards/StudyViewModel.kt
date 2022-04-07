package es.uam.eps.dadm.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.time.LocalDateTime

class StudyViewModel : ViewModel() {
    var card: Card? = null
    private var cards: MutableList<Card> = CardsApplication.cards

    private var _nDueCards = MutableLiveData<Int>()
    val nDueCards: LiveData<Int>
        get() = _nDueCards

    init {
        card = randomCard()
        _nDueCards.value = dueCards().size
    }

    private fun dueCards(): List<Card> {
        val currentDate = LocalDateTime.now()
        return cards.filter { c -> c.isDue(currentDate) }
    }

    private fun randomCard() =  try {
        dueCards().random()
    } catch (e: NoSuchElementException) {
        null
    }

    fun update(quality: Int) {
        card?.updateCard(quality)
        card = randomCard()

        _nDueCards.value = nDueCards.value?.minus(1)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("StudyViewModel destroyed")
    }
}