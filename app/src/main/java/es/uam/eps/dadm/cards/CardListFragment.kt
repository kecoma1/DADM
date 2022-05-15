package es.uam.eps.dadm.cards

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import java.util.concurrent.Executors

class CardListFragment: Fragment() {
    private lateinit var adapter: CardAdapter
    private lateinit var auth: FirebaseAuth
    private val executor = Executors.newSingleThreadExecutor()

    private val cardListViewModel by lazy {
        ViewModelProvider(this).get(CardListViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_card_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { true }
            R.id.settings -> {
                startActivity(Intent(requireContext(), SettingsActivity::class.java))
            }
            R.id.log_out -> {
                auth.signOut()
                this.findNavController()
                    .navigate(CardListFragmentDirections
                        .actionCardListFragmentToLoginFragment())
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

        SettingsActivity.setLoggedIn(requireContext(), true)

        val args = CardListFragmentArgs.fromBundle(requireArguments())

        adapter = CardAdapter()
        adapter.data = emptyList()
        binding.cardListRecyclerView.adapter = adapter

        binding.newCardFab.setOnClickListener {
            val card = Card("", "", deckId = args.deckId)
            executor.execute {
                CardDatabase.getInstance(this.requireContext()).cardDao.addCard(card)
            }

            it.findNavController()
                .navigate(CardListFragmentDirections.actionCardListFragmentToCardEditFragment(card.id))
        }

        cardListViewModel.deckCards(args.deckId).observe(viewLifecycleOwner) {
            adapter.data = it
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}