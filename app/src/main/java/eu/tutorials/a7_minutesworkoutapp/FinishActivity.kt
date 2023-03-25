package eu.tutorials.a7_minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.tutorials.a7_minutesworkoutapp.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private var viewBinding : ActivityFinishBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        setSupportActionBar(viewBinding?.toolbarFinishActivity)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        viewBinding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        viewBinding?.btnFinish?.setOnClickListener {
            finish()
        }
    }
}