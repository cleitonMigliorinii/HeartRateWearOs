package br.utfpr.frequenciawear.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HeartRateViewModel : ViewModel() {

    private val _heartRate = mutableStateOf(0)

    val heartRate: State<Int> get() = _heartRate

    private var lastUpdateScreen = 0L

    fun updateHeartRate(newValueHeartRate: Float){

        val now = System.currentTimeMillis()

        if(now - lastUpdateScreen > 5000){

            _heartRate.value = newValueHeartRate.toInt()

            lastUpdateScreen = now

        }

    }

}