package es.uam.eps.dadm.cards

import android.app.Application
import android.os.Bundle
import timber.log.Timber

class CardsApplication : Application() {

    init {
        // Creating cards
        cards.apply {
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
    }

    fun onCreate(savedInstanceState: Bundle?) {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var cards: MutableList<Card> = mutableListOf()
    }
}