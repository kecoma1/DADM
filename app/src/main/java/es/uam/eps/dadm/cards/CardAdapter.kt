package es.uam.eps.dadm.cards


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.cards.databinding.ListItemCardBinding


class CardAdapter : RecyclerView.Adapter<CardAdapter.CardHolder>() {
    private lateinit var binding: ListItemCardBinding
    var data = listOf<Card>()

    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var local = binding

        fun bind(card: Card, position: Int) {
            local.card = card

            // Listener for the card it self
            itemView.setOnClickListener {
                it.findNavController()
                    .navigate(CardListFragmentDirections.actionCardListFragmentToCardEditFragment(card.id))
            }

            // Lambda for hiding additional data.
            val hideUnHide = {
                val visibility = if (local.repetitions.visibility == View.VISIBLE) View.INVISIBLE
                else View.VISIBLE

                card.detailsHidden = visibility != View.VISIBLE

                notifyItemChanged(position)
            }

            local.deleteButton.setOnClickListener {
                CardsApplication.delCard(card)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, data.size)
            }
            local.hideDetails.setOnClickListener { hideUnHide() }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemCardBinding.inflate(layoutInflater, parent, false)
        return CardHolder(binding.root)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(
        holder: CardHolder,
        position: Int
    ) {
        holder.bind(data[position], position)
    }
}