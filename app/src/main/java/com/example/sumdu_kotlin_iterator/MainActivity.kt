package com.example.sumdu_kotlin_iterator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sumdu_kotlin_iterator.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

/**
 * MainActivity is an AppCompatActivity that demonstrates the iteration of a range of numbers
 * with a configurable delay between iterations. It uses MainViewModel to handle the state and
 * business logic.
 */
class MainActivity : AppCompatActivity() {

    // View binding instance for accessing views in the layout
    private lateinit var binding: ActivityMainBinding

    // ViewModel instance to handle state and business logic
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up a click listener for the start button
        binding.startButton.setOnClickListener {
            val startValue = binding.startValue.text.toString().toIntOrNull()
            val endValue = binding.endValue.text.toString().toIntOrNull()

            if (startValue != null && endValue != null) {
                viewModel.setIteration(startValue, endValue)
            }
        }

        // Set up a listener for the delay SeekBar
        binding.delaySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // Update the delay time in the ViewModel when the SeekBar progress changes
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.updateDelayTime(progress.toLong())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Observe the counterFlow from the ViewModel and update the UI accordingly
        lifecycleScope.launch {
            viewModel.counterFlow.collect { counter ->
                binding.tv.text = "i = $counter"
                binding.hashcodeText.text = "hashCode: ${counter.hashCode()}"
            }
        }
    }
}
