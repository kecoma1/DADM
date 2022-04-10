package es.uam.eps.dadm.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding

class CardListFragment: Fragment() {
    private lateinit var adapter: CardAdapter
    private lateinit var deck: Deck

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentCardListBinding>(
            inflater,
            R.layout.fragment_card_list,
            container,
            false
        )

        val args = CardListFragmentArgs.fromBundle(requireArguments())
        deck = CardsApplication.getDeck(args.deckId) ?: throw Exception("Wrong id")
        binding.deck = deck

        adapter = CardAdapter()
        adapter.data = deck.cards
        binding.cardListRecyclerView.adapter = adapter

        binding.newCardFab.setOnClickListener {
            val card = Card("", "")
            CardsApplication.addCard(card)

            it.findNavController()
                .navigate(CardListFragmentDirections.actionCardListFragmentToCardEditFragment(card.id))
        }

        return binding.root
    }
}