package es.uam.eps.dadm.cards


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import es.uam.eps.dadm.cards.databinding.ActivityStudyBinding
import timber.log.Timber


class StudyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.plant(Timber.DebugTree())
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study)
    }
}