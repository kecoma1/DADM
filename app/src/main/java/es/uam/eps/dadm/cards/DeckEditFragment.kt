package es.uam.eps.dadm.cards

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.FragmentDeckEditBinding
import java.util.concurrent.Executors

class DeckEditFragment : Fragment() {
    lateinit var binding: FragmentDeckEditBinding
    lateinit var deck: Deck
    lateinit var name: String

    private val executor = Executors.newSingleThreadExecutor()

    private val viewModel by lazy {
        ViewModelProvider(this).get(DeckEditViewModel::class.java)
    }

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

        // Loading the deck to edit
        val args = DeckEditFragmentArgs.fromBundle(requireArguments())
        viewModel.loadDeckId(args.deckId)
        viewModel.deck.observe(viewLifecycleOwner) {
            deck = it[0].deck
            binding.deck = deck
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // Text watcher for the name of the deck
        val nameTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                deck.name = s.toString()
            }
        }
        binding.nameEditText.addTextChangedListener(nameTextWatcher)

        // Lambda to go back to the deck list
        val goToDeckListFragment = { view: View ->
            view.findNavController()
                .navigate(DeckEditFragmentDirections
                    .actionDeckEditFragmentToDeckListFragment())
        }

        // Lambda to restore the name of the deck in case there's no change
        val restore = { deck.name = name }

        // Accept button on click listener
        binding.acceptDeckEditButton.setOnClickListener {
            executor.execute {
                CardDatabase.getInstance(this.requireContext()).cardDao.updateDeck(deck)
            }

            if (deck.name.isEmpty())
                Snackbar.make(it, resources.getString(R.string.ask_for_values), Snackbar.LENGTH_LONG).show()
            else goToDeckListFragment(it)
        }

        // Cancel button on click listener
        binding.cancelDeckEditButton.setOnClickListener {

            if (deck.name.isEmpty())
                executor.execute {
                    CardDatabase.getInstance(this.requireContext()).cardDao.deleteDeck(deck)
                }
            restore()
            goToDeckListFragment(it)
        }
    }

    override fun onStop() {
        super.onStop()

        // When back is pressed instead of "cancel"
        if (deck.name.isEmpty())
            executor.execute {
                CardDatabase.getInstance(this.requireContext()).cardDao.deleteDeck(deck)
            }
    }
}