package es.uam.eps.dadm.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import es.uam.eps.dadm.cards.databinding.ActivityTitleBinding
import timber.log.Timber

class TitleActivity : AppCompatActivity() {
    lateinit var binding: ActivityTitleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference = database.getReference("message")
        reference.setValue("Hello from Cards")

        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Snackbar.make(binding.root, snapshot.value.toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {}

        })

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        binding.navView.setupWithNavController(navHostFragment.navController)

        PreferenceManager.setDefaultValues(
            this,
            R.xml.root_preferences,
            false
        )
    }
}