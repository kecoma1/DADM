package es.uam.eps.dadm.cards


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.cards.databinding.ListItemCardBinding
import es.uam.eps.dadm.cards.databinding.ListItemDeckBinding


class DeckAdapter : RecyclerView.Adapter<DeckAdapter.DeckHolder>() {
    private lateinit var binding: ListItemDeckBinding
    var data = listOf<Deck>()

    inner class DeckHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var local = binding

        fun bind(deck: Deck, position: Int) {
            local.deck = deck

            itemView.setOnClickListener {
                CardsApplication.currentDeck = deck
                it.findNavController()
                    .navigate(
                        DeckListFragmentDirections
                            .actionDeckListFragmentToCardListFragment(deck.id))
            }

            local.deleteButton.setOnClickListener {
                CardsApplication.delDeck(deck)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, data.size)
            }

            local.editButton.setOnClickListener {
                it.findNavController()
                    .navigate(
                        DeckListFragmentDirections
                            .actionDeckListFragmentToDeckEditFragment(deck.id))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeckHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemDeckBinding.inflate(layoutInflater, parent, false)
        return DeckHolder(binding.root)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(
        holder: DeckHolder,
        position: Int
    ) {
        holder.bind(data[position], position)
    }
}