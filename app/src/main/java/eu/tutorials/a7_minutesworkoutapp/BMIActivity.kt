package eu.tutorials.a7_minutesworkoutapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import eu.tutorials.a7_minutesworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.channels.FileLock

class BMIActivity : AppCompatActivity() {
    var binding : ActivityBmiBinding ? = null


    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNITS_VIEW"
    }
    var currentVisibleView : String = METRIC_UNITS_VIEW
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()
            binding?.rgUnits?.setOnCheckedChangeListener{
                _,checkedId : Int ->
                if(checkedId == R.id.rbMetricUnits){
                    makeVisibleMetricUnitsView()
                }else{
                    makeVisibleUsUnitsView()
                }
            }

        //binding?.background?.setBackgroundColor(Color.WHITE)

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }

    }
    private fun displayBMIresults(bmi : Float){

        var bmiLabel = ""
        var bmiDesc = ""

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "very severly underweight"
            bmiDesc = "take care of yourself"
        }else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "underweight"
            bmiDesc = "take care of yourself"
        }else{
            bmiLabel = "bhala achu"
            bmiDesc = "maddi chala"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDesc
    }
    private fun validateMetricUnits() : Boolean{
        var isValid = true
        if(binding?.etMetricUnitWeight?.text.toString().isEmpty())
            isValid = false
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty())
            isValid = false

        return isValid
    }

    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNITS_VIEW){
            if(validateMetricUnits()){
                val height : Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weight : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val BMI : Float = (weight / (height * height))
                displayBMIresults(BMI)
            }else{
                Toast.makeText(this@BMIActivity,"Please enter Valid values",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            if(validateUsUnits()){
                val heightFeet : Float = binding?.etUsMetricUnitHeightFeet?.text.toString().toFloat()
                val heightInch : Float = binding?.etUsMetricUnitHeightInch?.text.toString().toFloat()

                val value : Float = binding?.etUsMetricUnitWeight?.text.
                toString().toFloat()

                val heightValue = heightInch + heightFeet * 12

                val bmi = 703 * (value / (heightValue * heightValue))

                displayBMIresults(bmi)
            }else{
                Toast.makeText(this@BMIActivity,"Please enter Valid values",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUsUnits() : Boolean{
        var isValid = true
        if(binding?.etUsMetricUnitWeight?.text.toString().isEmpty())
            isValid = false
        else if(binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty())
            isValid = false
        else if(binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty())
            isValid = false

        return isValid
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW // Current View is updated here.
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE // METRIC  Height UNITS VIEW is Visible
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE // METRIC  Weight UNITS VIEW is Visible
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE // make weight view Gone.
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE // make height feet view Gone.
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE // make height inch view Gone.

        binding?.etMetricUnitHeight?.text!!.clear() // height value is cleared if it is added.
        binding?.etMetricUnitWeight?.text!!.clear() // weight value is cleared if it is added.

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }
    // END

    // TODO(Step 4 : Making a function to make the US UNITS view visible.)
    // START
    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW // Current View is updated here.
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE // METRIC  Height UNITS VIEW is InVisible
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE // METRIC  Weight UNITS VIEW is InVisible
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE // make weight view visible.
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE // make height feet view visible.
        binding?.tilMetricUsUnitHeightInch?.visibility = View.VISIBLE // make height inch view visible.

        binding?.etUsMetricUnitWeight?.text!!.clear() // weight value is cleared.
        binding?.etUsMetricUnitHeightFeet?.text!!.clear() // height feet value is cleared.
        binding?.etUsMetricUnitHeightInch?.text!!.clear() // height inch is cleared.

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }
}