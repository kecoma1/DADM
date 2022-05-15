package es.uam.eps.dadm.cards

import android.app.Application
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.cards.database.CardDatabase
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.Executors

class StudyViewModel(application: Application) : AndroidViewModel(application) {
    private val executor = Executors.newSingleThreadExecutor()
    private val context = getApplication<Application>().applicationContext
    var card: Card? = null
    var cards: LiveData<List<Card>> = CardDatabase
        .getInstance(context)
        .cardDao
        .getNCards(
            Firebase.auth.currentUser!!.uid,
            SettingsActivity.getMaximumNumberOfCards(context)!!.toInt()
        )

    var dueCard: LiveData<Card?> = Transformations.map(cards) {
        try {
            it.filter { c -> c.isDue() }.random()
        } catch (e: Exception) {
            null
        }
    }

    private var _nDueCards = MutableLiveData<Int>()
    var nDueCards: LiveData<Int> = Transformations.map(cards) {
        it.filter { c -> c.isDue() }.size
    }

    fun update(quality: Int) {
        when (quality) {
            5 -> easyQuestions++
            3 -> mediumQuestions++
            else -> hardQuestions++
        }

        _nDueCards.value = nDueCards.value?.minus(1)

        card?.updateCard(quality)
        executor.execute {
            CardDatabase.getInstance(context).cardDao.updateCard(card!!)
        }
    }

    fun reload() {
        card = null
        cards = CardDatabase
                .getInstance(context)
                .cardDao
                .getNCards(
                    Firebase.auth.currentUser!!.uid,
                    SettingsActivity.getMaximumNumberOfCards(context)!!.toInt()
                )
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