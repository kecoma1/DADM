package es.uam.eps.dadm.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.uam.eps.dadm.cards.databinding.ActivityStudyBinding
import timber.log.Timber


private const val ANSWERED_KEY = "es.uam.eps.dadm.cards:answered"


class StudyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudyBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var listener = View.OnClickListener { v ->
        // Assign quality's value from v
        // depending on the button clicked
        val quality = when (v?.id) {
            binding.easyButton?.id -> 5
            binding.mediumButton?.id -> 3
            binding.hardButton?.id -> 0
            else -> 5
        }

        viewModel.update(quality)
        if (viewModel.card == null)
            Toast.makeText(this, R.string.no_more_cards, Toast.LENGTH_LONG).show()

        fadeAnimation(R.anim.fade_in, binding.answerButton!!)
        fadeAnimation(R.anim.fade_out, binding.difficultyButtons!!)
        binding.invalidateAll()
    }

    private var fadeAnimation: (Int, View) -> Unit = { fade, view ->

        // Loading the animation
        val anim: Animation = AnimationUtils.loadAnimation(this, fade)

        view.clearAnimation()
        view.startAnimation(anim)

        anim.setAnimationListener(object : Animation.AnimationListener {
            // We do not need to add login to the methods
            override fun onAnimationStart(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) {}
            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.viewModel?.card?.answered
        Timber.i("onRestoreInstanceState called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.i("onSaveInstanceState called")
        outState.putBoolean(ANSWERED_KEY, binding.viewModel?.card?.answered ?: false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.plant(Timber.DebugTree())
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study)

        binding.viewModel = viewModel
        Timber.i("$viewModel")

        viewModel.nDueCards.observe(this, Observer<Int> {
            binding.infoTextView?.text = it.toString()
        })

        binding.apply {
            // Listener for the answer button
            answerButton?.setOnClickListener {
                viewModel?.card?.answered = true
                // Animations
                fadeAnimation(R.anim.fade_in, answerTextView!!)
                fadeAnimation(R.anim.fade_in, easyButton!!)
                fadeAnimation(R.anim.fade_in, mediumButton!!)
                fadeAnimation(R.anim.fade_in, hardButton!!)
                fadeAnimation(R.anim.fade_out, answerButton)
                invalidateAll()
            }

            easyButton?.setOnClickListener(listener)
            mediumButton?.setOnClickListener(listener)
            hardButton?.setOnClickListener(listener)
        }
        Timber.i("onCreate called")
    }

    // For animations when the back button is pressed
    override fun finish() {
        super.finish()
        // For transitions without XML
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")
    }

    override fun onResume() {
        super.onResume()
        binding.invalidateAll()
        Timber.i("onResume called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause called")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy called")
    }
}