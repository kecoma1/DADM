package es.uam.eps.dadm.cards

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import es.uam.eps.dadm.cards.databinding.FragmentCardEditBinding
import es.uam.eps.dadm.cards.databinding.FragmentDeckEditBinding

class DeckEditFragment : Fragment() {
    lateinit var binding: FragmentDeckEditBinding
    lateinit var deck: Deck
    lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_deck_edit,
            container,
            false
        )

        val args = DeckEditFragmentArgs.fromBundle(requireArguments())
        deck = CardsApplication.getDeck(args.deckId) ?: throw Exception("Wrong id")
        binding.deck = deck
        name = deck.name

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val nameTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                deck.name = s.toString()
            }
        }

        binding.nameEditText.addTextChangedListener(nameTextWatcher)

        val goToDeckListFragment = { view: View ->
            view.findNavController()
                .navigate(DeckEditFragmentDirections
                    .actionDeckEditFragmentToDeckListFragment())
        }

        val restore = { deck.name = name }

        binding.acceptCardEditButton.setOnClickListener {
            if (deck.name.isEmpty())
                Snackbar.make(it, resources.getString(R.string.ask_for_values), Snackbar.LENGTH_LONG).show()
            else goToDeckListFragment(it)
        }
        binding.cancelCardEditButton.setOnClickListener {
            if (deck.name.isEmpty())
                CardsApplication.delDeck(deck)
            restore()
            goToDeckListFragment(it)
        }
    }
}