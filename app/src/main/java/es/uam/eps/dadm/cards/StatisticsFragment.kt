package es.uam.eps.dadm.cards


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.snackbar.Snackbar
import es.uam.eps.dadm.cards.databinding.FragmentStatisticsBinding


class StatisticsFragment: Fragment() {

    private val viewModel: StatisticViewModel by lazy {
        ViewModelProvider(this).get(StatisticViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentStatisticsBinding>(
            inflater,
            R.layout.fragment_statistics,
            container,
            false
        )

        viewModel.decks.observe(viewLifecycleOwner) {
            var message = String()
            for (deck in it)
                message += "The deck named ${deck.deck.name} has ${deck.cards.size} cards\n"
            Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
        }

        // Setting the fragment variables
        binding.viewModel = viewModel
        binding.numberDecks = CardsApplication.numberDecks

        // Setting the entries for the Pie chart
        val entries = mutableListOf<PieEntry>()
        entries.apply {
            add(PieEntry(StudyViewModel.easyQuestions.toFloat(), resources.getString(R.string.easy)))
            add(PieEntry(StudyViewModel.mediumQuestions.toFloat(), resources.getString(R.string.medium)))
            add(PieEntry(StudyViewModel.hardQuestions.toFloat(), resources.getString(R.string.hard)))
        }

        // Setting the colors
        val colors = mutableListOf<Int>()
        for (i in 0..2) colors.add(ColorTemplate.MATERIAL_COLORS[i])

        // Setting the data and plotting
        val dataset = PieDataSet(entries, resources.getString(R.string.difficulty_pie_title))
        dataset.colors = colors
        val data = PieData(dataset)
        data.setDrawValues(true)
        data.setValueTextSize(12f)
        binding.chart.data = data
        binding.chart.invalidate()

        return binding.root
    }
}
