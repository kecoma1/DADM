package es.uam.eps.dadm.cards


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.ListItemDeckBinding
import java.util.concurrent.Executors


class DeckAdapter : RecyclerView.Adapter<DeckAdapter.DeckHolder>() {
    private lateinit var binding: ListItemDeckBinding
    var data = listOf<Deck>()

    inner class DeckHolder(view: View, private var context: Context) : RecyclerView.ViewHolder(view) {

        private var local = binding
        private val executor = Executors.newSingleThreadExecutor()

        fun bind(deck: Deck, position: Int) {
            local.deck = deck

            itemView.setOnClickListener {
                CardsApplication.currentDeck = deck
                it.findNavController()
                    .navigate(
                        DeckListFragmentDirections
                            .actionDeckListFragmentToCardListFragment(deck.deckId))
            }

            local.deleteButton.setOnClickListener {
                executor.execute {
                    val dao = CardDatabase.getInstance(context).cardDao
                    dao.deleteDeckCards(deck.deckId)
                    dao.deleteDeck(deck)
                }
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, data.size)
            }

            local.editButton.setOnClickListener {
                it.findNavController()
                    .navigate(
                        DeckListFragmentDirections
                            .actionDeckListFragmentToDeckEditFragment(deck.deckId))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeckHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemDeckBinding.inflate(layoutInflater, parent, false)
        return DeckHolder(binding.root, parent.context)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(
        holder: DeckHolder,
        position: Int
    ) {
        holder.bind(data[position], position)
    }
}