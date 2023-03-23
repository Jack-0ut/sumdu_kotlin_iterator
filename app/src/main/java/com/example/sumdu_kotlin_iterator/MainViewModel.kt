package com.example.sumdu_kotlin_iterator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.asStateFlow

/**
 * MainViewModel is a ViewModel class responsible for managing the state
 * of the main screen in the app. It handles the iteration through a given range of numbers
 * with a configurable delay between the iterations.
 *
 * This ViewModel provides a StateFlow<Int> to observe the current value of the iteration.
 * The iteration can be controlled by setting the start and end values and adjusting the delay
 * time between iterations.
 */
class MainViewModel : ViewModel() {

    // Holds the current value of the iteration to be observed by the UI
    private val _counterFlow = MutableStateFlow(0)

    // Public StateFlow to be observed by the UI
    val counterFlow: StateFlow<Int> get() = _counterFlow

    // The delay time (in milliseconds) between iterations
    private var delayTime = 1000L

    // The start value of the iteration range
    private var startValue = 1

    // The end value of the iteration range
    private var endValue = 10

    // Holds the reference to the current iteration job to allow cancellation
    private var currentJob: Job? = null

    /**
     * Updates the delay time between iterations.
     *
     * @param newDelayTime The new delay time in milliseconds.
     */
    fun updateDelayTime(newDelayTime: Long) {
        delayTime = newDelayTime
    }

    /**
     * Sets the iteration range and starts a new iteration.
     *
     * @param start The start value of the iteration range.
     * @param end The end value of the iteration range.
     */
    fun setIteration(start: Int, end: Int) {
        startValue = start
        endValue = end
        _counterFlow.value = startValue // Reset the counter value to clear the text field
        startIteration()
    }

    /**
     * Starts a new iteration through the range of numbers with the configured delay time.
     * Cancels any ongoing iteration before starting a new one.
     */
    private fun startIteration() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            for (i in startValue..endValue) {
                delay(delayTime)
                _counterFlow.emit(i)
            }
        }
    }
}
