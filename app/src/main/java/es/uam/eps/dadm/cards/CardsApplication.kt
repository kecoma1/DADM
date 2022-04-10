package es.uam.eps.dadm.cards

import android.app.Application

class CardsApplication : Application() {

    init {
        // Creating decks
        val japanese = Deck("Japanese")
        val quotes = Deck("Quotes")

        // Adding cards
        japanese.cards.apply {
            add(Card("か", "ka"))
            add(Card("き", "ki")); add(Card("く", "ku")); add(Card("け", "ke")); add(Card("こ", "ko")); add(Card("さ", "sa")); add(Card("し", "shi")); add(Card("す", "su")); add(Card("せ", "se")); add(Card("そ", "so"))
            add(Card("た", "ta")); add(Card("ち", "chi")); add(Card("つ", "tsu")); add(Card("て", "te")); add(Card("と", "to")); add(Card("な", "na")); add(Card("に", "ni")); add(Card("ぬ", "nu")); add(Card("ね", "ne"))
            add(Card("の", "no")); add(Card("は", "ha")); add(Card("ひ", "hi")); add(Card("ふ", "fu")); add(Card("へ", "he")); add(Card("ほ", "ho")); add(Card("ま", "ma")); add(Card("み", "mi")); add(Card("む", "mu"))
            add(Card("め", "me")); add(Card("も", "mo")); add(Card("や", "ya")); add(Card("ゆ", "yu")); add(Card("よ", "yo")); add(Card("ら", "ra")); add(Card("り", "ri")); add(Card("る", "ru")); add(Card("れ", "re"))
            add(Card("ろ", "ro")); add(Card("わ", "wa")); add(Card("ゐ", "wi")); add(Card("ゑ", "we")); add(Card("を", "wo")); add(Card("ん", "n")); add(Card("が", "ga")); add(Card("ぎ", "gi")); add(Card("ぐ", "gu"))
            add(Card("げ", "ge")); add(Card("ご", "go")); add(Card("ぎゃ", "gya")); add(Card("ぎゅ", "gyu")); add(Card("ぎょ", "gyo")); add(Card("ぐゎ", "gwa")); add(Card("ざ", "za")); add(Card("じ", "ji")); add(Card("ず", "zu"))
            add(Card("ぜ", "ze")); add(Card("ぞ", "zo")); add(Card("じゃ", "ja")); add(Card("じゅ", "ju")); add(Card("じょ", "jo")); add(Card("だ", "da")); add(Card("ぢ", "dji")); add(Card("づ", "dzu")); add(Card("で", "de"))
            add(Card("ど", "do")); add(Card("ぢゃ", "dja")); add(Card("ぢゅ", "dju")); add(Card("ぢょ", "djo")); add(Card("ば", "ba")); add(Card("び", "bi")); add(Card("ぶ", "bu")); add(Card("べ", "be")); add(Card("ぼ", "bo"))
            add(Card("びゃ", "bya")); add(Card("びゅ", "byu")); add(Card("びょ", "byo")); add(Card("ぱ", "pa")); add(Card("ぴ", "pi")); add(Card("ぷ", "pu")); add(Card("ぺ", "pe")); add(Card("ぽ", "po")); add(Card("ぴゃ", "pya"))
            add(Card("ぴゅ", "pyu")); add(Card("ぴょ", "pyo"))
        }

        quotes.cards.apply {
            add(Card("El hombre es mortal por sus temores e inmortal por sus deseos", "Anaxagoras"))
            add(Card("No pienses que lo que se te dificulta es humanamente imposible; y si es humanamente posible, consideralo a tu alcance", "Marco Aurelio"))
            add(Card("La meta de todo aprendizaje no es el dinero, una buena posición, un título o un diploma, sino la transformación de tu mente en el camino a la maestría", "Robert Greene"))
            add(Card("El amor nunca se muere de hambre, pero sí de indigestión", "Ninon"))
            add(Card("Cualquier hombre que intente ser bueno todo el tiempo terminará yendo a la ruina entre la gran catidad de hombres que no lo son. Por lo tanto, un príncipe que quiera conservar su autoridad deberá aprender a no ser bueno usar ese conocimiento, o prescindir de su uso, según las necesidades que se presenten", "\"El príncipe\", Nicolás Maquiavelo"))
            add(Card("El hombre no puede rehacerse a sí mismo sin sufrir, porque es a la vez el mármol y el escultor", "Alexis Carrel"))
            add(Card("Controla tu imaginación, domina los sentimientos de tu corazón, apaga tus deseos y procura que tu alma sea dueña de si misma", "Marco Aurelio"))
            add(Card("El vicio, considerado en términos generales, no causa perjuicio al universo; y considerado aparte, no es un mal para los demás. Sólo es perjudicial a quien no tiene la facultad de contrarrestarle oponiéndose una firme voluntad", "Marco Aurelio"))
            add(Card("El secreto del cambio es enfocar toda tu Energía, no en luchar contra lo viejo, sino en construir lo nuevo", "Socrates"))
            add(Card("El hombre que dice que puede, y el hombre que dice que no puede, ambos tienen razón", "Confuncio"))
            add(Card("Todo hombre que conozco es superior a mí en algún sentido. En ese sentido, aprendo de él", "Ralph Waldo Emerson"))
        }

        // Adding the decks to the application
        decks.apply {
            add(japanese)
            add(quotes)
        }

        cards.apply {
            addAll(japanese.cards)
            addAll(quotes.cards)
        }
    }

    companion object {
        var cards: MutableList<Card> = mutableListOf()
        var decks: MutableList<Deck> = mutableListOf()
        var currentDeck: Deck? = null

        // Card functions
        fun numberOfDueCards(): Int { return currentDeck?.cards!!.count { c -> c.isDue() } }
        fun getCard(id: String): Card? { return currentDeck?.cards!!.find { c -> c.id == id } }
        fun addCard(card: Card) { currentDeck?.cards!!.add(card) }
        fun delCard(card: Card) { currentDeck?.cards!!.remove(card) }

        // Deck functions
        fun getDeck(id: String): Deck? { return decks.find { d -> d.id == id } }
        fun addDeck(deck: Deck) { decks.add(deck) }
        fun delDeck(deck: Deck) { decks.remove(deck) }
    }
}