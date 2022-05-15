package es.uam.eps.dadm.cards

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.FragmentDeckListBinding
import java.util.concurrent.Executors

class DeckListFragment: Fragment() {
    private lateinit var adapter: DeckAdapter

    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    private val deckListViewModel by lazy {
        ViewModelProvider(this).get(DeckListViewModel::class.java)
    }

    private val cardListViewModel by lazy {
        ViewModelProvider(this).get(CardListViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth

        // A user is logged in in this fragment
        reference = FirebaseDatabase.getInstance().getReference(auth.currentUser!!.uid)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_card_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(requireContext(), SettingsActivity::class.java))
            }
            R.id.log_out -> {
                auth.signOut()
                this.findNavController()
                    .navigate(DeckListFragmentDirections
                        .actionDeckListFragmentToLoginFragment())
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

        binding.uploadButton.setOnClickListener {
            reference.child("decks").setValue(deckListViewModel.decks)
            reference.child("cards").setValue(cardListViewModel.cards)
            Snackbar.make(binding.root, R.string.upload_success, Snackbar.LENGTH_SHORT).show()
        }

        // Loading cards and decks
        cardListViewModel.cards.observe(viewLifecycleOwner) {}
        deckListViewModel.decks.observe(viewLifecycleOwner) {
            adapter.data = it
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}