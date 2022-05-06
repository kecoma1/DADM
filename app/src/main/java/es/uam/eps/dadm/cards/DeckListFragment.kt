package es.uam.eps.dadm.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.FragmentDeckListBinding
import java.util.concurrent.Executors

class DeckListFragment: Fragment() {
    private lateinit var adapter: DeckAdapter

    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentDeckListBinding>(
            inflater,
            R.layout.fragment_deck_list,
            container,
            false
        )
        adapter = DeckAdapter()
        adapter.data = CardsApplication.decks
        binding.deckListRecyclerView.adapter = adapter

        binding.newDeckFab.setOnClickListener {
            val deck = Deck(name = "", deckId = Deck.lastDeckId+1)
            executor.execute {
                CardDatabase.getInstance(this.requireContext()).cardDao.addDeck(deck)
            }

            it.findNavController()
                .navigate(DeckListFragmentDirections.actionDeckListFragmentToDeckEditFragment(deck.deckId))
        }

        return binding.root
    }
}