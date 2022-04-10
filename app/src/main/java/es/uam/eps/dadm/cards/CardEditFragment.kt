package es.uam.eps.dadm.cards

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import es.uam.eps.dadm.cards.databinding.FragmentCardEditBinding

class CardEditFragment : Fragment() {
    lateinit var binding: FragmentCardEditBinding
    lateinit var card: Card
    lateinit var question: String
    lateinit var answer: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_card_edit,
            container,
            false
        )

        val args = CardEditFragmentArgs.fromBundle(requireArguments())
        card = CardsApplication.getCard(args.cardId) ?: throw Exception("Wrong id")
        binding.card = card
        question = card.question
        answer = card.answer

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val questionTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                card.question = s.toString()
            }
        }

        val answerTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) { }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                card.answer = s.toString()
            }

        }

        binding.questionEditText.addTextChangedListener(questionTextWatcher)
        binding.answerEditText.addTextChangedListener(answerTextWatcher)

        val goToCardListFragment = { view: View ->
            view.findNavController()
                .navigate(CardEditFragmentDirections
                    .actionCardEditFragmentToCardListFragment(CardsApplication
                        .currentDeck!!.id))
        }

        val restore = {
            card.question = question
            card.answer = answer
        }

        binding.acceptCardEditButton.setOnClickListener {
            if (card.question.isEmpty() || card.answer.isEmpty())
                Snackbar.make(it, resources.getString(R.string.ask_for_values), Snackbar.LENGTH_LONG).show()
            else goToCardListFragment(it)
        }
        binding.cancelCardEditButton.setOnClickListener {
            if (card.question.isEmpty() || card.answer.isEmpty())
                CardsApplication.delCard(card)
            restore()
            goToCardListFragment(it) }
    }
}