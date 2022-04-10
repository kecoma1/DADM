package es.uam.eps.dadm.cards


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import es.uam.eps.dadm.cards.databinding.FragmentStatisticsBinding


class StatisticsFragment: Fragment() {

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
