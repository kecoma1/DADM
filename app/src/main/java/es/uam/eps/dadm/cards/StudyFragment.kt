package es.uam.eps.dadm.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import es.uam.eps.dadm.cards.databinding.ActivityStudyBinding
import es.uam.eps.dadm.cards.databinding.FragmentStudyBinding
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding
import timber.log.Timber


private const val ANSWERED_KEY = "es.uam.eps.dadm.cards:answered"


class StudyFragment : Fragment() {

    private lateinit var binding: FragmentStudyBinding
    private val viewModel: StudyViewModel by lazy {
        ViewModelProvider(this).get(StudyViewModel::class.java)
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
            Toast.makeText(activity, R.string.no_more_cards, Toast.LENGTH_LONG).show()

        fadeAnimation(R.anim.fade_in, binding.answerButton!!)
        fadeAnimation(R.anim.fade_out, binding.difficultyButtons!!)
        binding.invalidateAll()
    }

    private var fadeAnimation: (Int, View) -> Unit = { fade, view ->

        // Loading the animation
        val anim: Animation = AnimationUtils.loadAnimation(activity, fade)

        view.clearAnimation()
        view.startAnimation(anim)

        anim.setAnimationListener(object : Animation.AnimationListener {
            // We do not need to add login to the methods
            override fun onAnimationStart(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) {}
            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.i("onSaveInstanceState called")
        outState.putBoolean(ANSWERED_KEY, binding.viewModel?.card?.answered ?: false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         binding = DataBindingUtil.inflate<FragmentStudyBinding>(
            inflater,
            R.layout.fragment_study,
            container,
            false
        )

        binding.viewModel = viewModel

        binding.viewModel = viewModel
        Timber.i("$viewModel")

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

        return binding.root
    }
}