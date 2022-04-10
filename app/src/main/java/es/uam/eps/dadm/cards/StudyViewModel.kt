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

    private fun dueCards(): List<Card> { return cards.filter { c -> c.isDue() } }

    private fun randomCard() =  try {
        dueCards().random()
    } catch (e: NoSuchElementException) {
        null
    }

    fun updateCards() {
        cards = CardsApplication.cards
        card = randomCard()
        _nDueCards.value = dueCards().size
    }

    fun update(quality: Int) {
        when (quality) {
            5 -> easyQuestions++
            3 -> mediumQuestions++
            else -> hardQuestions++
        }

        card?.updateCard(quality)
        card = randomCard()

        _nDueCards.value = nDueCards.value?.minus(1)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("StudyViewModel destroyed")
    }

    companion object {
        var hardQuestions: Int = 0
        var mediumQuestions: Int = 0
        var easyQuestions: Int = 0
    }
}