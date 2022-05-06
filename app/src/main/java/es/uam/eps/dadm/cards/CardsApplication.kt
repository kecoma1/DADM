package es.uam.eps.dadm.cards

import android.app.Application
import es.uam.eps.dadm.cards.database.CardDatabase
import timber.log.Timber
import java.util.concurrent.Executors

class CardsApplication : Application() {
    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate() {
        super.onCreate()

        // Creating the database
        val cardDatabase = CardDatabase.getInstance(this)

        // Creating decks
        val japanese = Deck(deckId = 0, name = "Japanese")
        val quotes = Deck(deckId = 1, name = "Quotes")

        // Adding cards
//        japanese.cards.apply {
//            add(Card("か", "ka"))
//            add(Card("き", "ki")); add(Card("く", "ku")); add(Card("け", "ke")); add(Card("こ", "ko")); add(Card("さ", "sa")); add(Card("し", "shi")); add(Card("す", "su")); add(Card("せ", "se")); add(Card("そ", "so"))
//            add(Card("た", "ta")); add(Card("ち", "chi")); add(Card("つ", "tsu")); add(Card("て", "te")); add(Card("と", "to")); add(Card("な", "na")); add(Card("に", "ni")); add(Card("ぬ", "nu")); add(Card("ね", "ne"))
//            add(Card("の", "no")); add(Card("は", "ha")); add(Card("ひ", "hi")); add(Card("ふ", "fu")); add(Card("へ", "he")); add(Card("ほ", "ho")); add(Card("ま", "ma")); add(Card("み", "mi")); add(Card("む", "mu"))
//            add(Card("め", "me")); add(Card("も", "mo")); add(Card("や", "ya")); add(Card("ゆ", "yu")); add(Card("よ", "yo")); add(Card("ら", "ra")); add(Card("り", "ri")); add(Card("る", "ru")); add(Card("れ", "re"))
//            add(Card("ろ", "ro")); add(Card("わ", "wa")); add(Card("ゐ", "wi")); add(Card("ゑ", "we")); add(Card("を", "wo")); add(Card("ん", "n")); add(Card("が", "ga")); add(Card("ぎ", "gi")); add(Card("ぐ", "gu"))
//            add(Card("げ", "ge")); add(Card("ご", "go")); add(Card("ぎゃ", "gya")); add(Card("ぎゅ", "gyu")); add(Card("ぎょ", "gyo")); add(Card("ぐゎ", "gwa")); add(Card("ざ", "za")); add(Card("じ", "ji")); add(Card("ず", "zu"))
//            add(Card("ぜ", "ze")); add(Card("ぞ", "zo")); add(Card("じゃ", "ja")); add(Card("じゅ", "ju")); add(Card("じょ", "jo")); add(Card("だ", "da")); add(Card("ぢ", "dji")); add(Card("づ", "dzu")); add(Card("で", "de"))
//            add(Card("ど", "do")); add(Card("ぢゃ", "dja")); add(Card("ぢゅ", "dju")); add(Card("ぢょ", "djo")); add(Card("ば", "ba")); add(Card("び", "bi")); add(Card("ぶ", "bu")); add(Card("べ", "be")); add(Card("ぼ", "bo"))
//            add(Card("びゃ", "bya")); add(Card("びゅ", "byu")); add(Card("びょ", "byo")); add(Card("ぱ", "pa")); add(Card("ぴ", "pi")); add(Card("ぷ", "pu")); add(Card("ぺ", "pe")); add(Card("ぽ", "po")); add(Card("ぴゃ", "pya"))
//            add(Card("ぴゅ", "pyu")); add(Card("ぴょ", "pyo"))
//        }

//        val quotesCards = mutableListOf<Card>()
//
//        quotesCards.apply {
//            add(Card("El hombre es mortal por sus temores e inmortal por sus deseos", "Anaxagoras", deckId = 1))
//            add(Card("No pienses que lo que se te dificulta es humanamente imposible; y si es humanamente posible, consideralo a tu alcance", "Marco Aurelio", deckId = 1))
//            add(Card("La meta de todo aprendizaje no es el dinero, una buena posición, un título o un diploma, sino la transformación de tu mente en el camino a la maestría", "Robert Greene", deckId = 1))
//            add(Card("El amor nunca se muere de hambre, pero sí de indigestión", "Ninon", deckId = 1))
//            add(Card("Cualquier hombre que intente ser bueno todo el tiempo terminará yendo a la ruina entre la gran catidad de hombres que no lo son. Por lo tanto, un príncipe que quiera conservar su autoridad deberá aprender a no ser bueno usar ese conocimiento, o prescindir de su uso, según las necesidades que se presenten", "\"El príncipe\", Nicolás Maquiavelo", deckId = 1))
//            add(Card("El hombre no puede rehacerse a sí mismo sin sufrir, porque es a la vez el mármol y el escultor", "Alexis Carrel", deckId = 1))
//            add(Card("Controla tu imaginación, domina los sentimientos de tu corazón, apaga tus deseos y procura que tu alma sea dueña de si misma", "Marco Aurelio", deckId = 1))
//            add(Card("El vicio, considerado en términos generales, no causa perjuicio al universo; y considerado aparte, no es un mal para los demás. Sólo es perjudicial a quien no tiene la facultad de contrarrestarle oponiéndose una firme voluntad", "Marco Aurelio", deckId = 1))
//            add(Card("El secreto del cambio es enfocar toda tu Energía, no en luchar contra lo viejo, sino en construir lo nuevo", "Socrates", deckId = 1))
//            add(Card("El hombre que dice que puede, y el hombre que dice que no puede, ambos tienen razón", "Confuncio", deckId = 1))
//            add(Card("Todo hombre que conozco es superior a mí en algún sentido. En ese sentido, aprendo de él", "Ralph Waldo Emerson", deckId = 1))
//        }

        // Adding the decks to the application
        decks.apply {
            add(japanese)
            add(quotes)
        }

//        cards.apply {
//            addAll(japanese.cards)
//            addAll(quotes.cards)
//        }

        executor.execute {
            cardDatabase.apply {
//                cardDao.addDeck(quotes)
                //addAllCards(japanese.cards)
//                addAllCards(quotesCards)
            }
        }

        Timber.plant(Timber.DebugTree())
    }


    companion object {
        var decks: MutableList<Deck> = mutableListOf()
        var currentDeck: Deck? = null

        /*
        *
        * ATTENTION:
        * The code analyzer says that the next properties could be val instead of var. If we make
        * this change, we have a big mistake because these values are going to be changed during
        * the execution of the application.
        */
        var numberDecks : Int = decks.size
            get() = decks.size


//        fun decksInfo() : String {
//            var info = ""
//            decks.forEach { d -> info += d.name + " " + d.cards.size + " cards - " }
//            return info.substring(0, info.length-2)
//        }
    }
}