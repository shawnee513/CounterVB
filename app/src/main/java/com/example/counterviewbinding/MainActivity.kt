package com.example.counterviewbinding

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import com.example.counterviewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Initialize the binding object
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //Old way    
    //setContentView(R.layout.activity_main)
        
        //New Way - View Binding!
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set click listeners
        binding.activityMainBtSubmit.setOnClickListener { submitNumber() }
        binding.activityMainBtRandomNumber.setOnClickListener { generateRandomNumber() }
        binding.activityMainBtIncrement.setOnClickListener { changeNumber("+") }
        binding.activityMainBtDecrement.setOnClickListener { changeNumber("-") }

        //check bundle
        if(savedInstanceState != null){
            binding.activityMainTvNumber.text = savedInstanceState.getString("myNumber")
            binding.activityMainEtInterval.setText(savedInstanceState.getString("myInterval"))
            binding.activityMainTvSummary.text = savedInstanceState.getString("mySummary")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("myNumber", binding.activityMainTvNumber.text.toString())
        outState.putString("myInterval", binding.activityMainEtInterval.text.toString())
        outState.putString("mySummary", binding.activityMainTvSummary.text.toString())
    }

    private fun submitNumber(){
        var startingNumber = binding.activityMainEtNumberInput.text.toString()
        if(startingNumber == ""){
            startingNumber = "10"
        }

        binding.activityMainTvNumber.text = startingNumber
        binding.activityMainEtNumberInput.setText("")
        hideKeyboard()
    }

    private fun generateRandomNumber(){
        val randomNumber = (-100..100).random()
        binding.activityMainTvNumber.text = randomNumber.toString()
    }

    private fun changeNumber(operation: String){
        //Get the current number
        val currentNumber = binding.activityMainTvNumber.text.toString().toInt()
        var value = binding.activityMainEtInterval.text.toString() //don't .toInt() yet since we have to check if it is blank and assign a default value if so

        //Check to see if the increment/decrement value is blank, if so default to 1
        if (value == ""){
            value = "1"
        }

        //Either increment or decrement based on the value of operation
        if(operation == "+"){
            //Determine new number to display and display it
            val newNumber = currentNumber + value.toInt()
            binding.activityMainTvNumber.text = newNumber.toString()

            //update the summary message
            binding.activityMainTvSummary.text = "$currentNumber + $value = $newNumber"

        }else{
            //Determine new number to display and display it
            val newNumber = currentNumber - value.toInt()
            binding.activityMainTvNumber.text = newNumber.toString()

            //update the summary message
            binding.activityMainTvSummary.text = "$currentNumber - $value = $newNumber"
        }

        //Hide the keyboard
        hideKeyboard()
    }

    private fun hideKeyboard(){
        var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.activityMainBtIncrement.windowToken, 0)
    }
}