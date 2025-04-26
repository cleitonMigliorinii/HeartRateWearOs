package br.utfpr.frequenciawear.presentation.test

import br.utfpr.frequenciawear.presentation.HeartRateViewModel
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class HeartRateViewModeTest {

@Test
fun testHeartRateUpdate() = runTest {

        val viewModel = HeartRateViewModel()

        viewModel.updateHeartRate(80f)
        assertEquals(80, viewModel.heartRate.value)

        Thread.sleep(3000)
        viewModel.updateHeartRate(85f)
        assertEquals(80, viewModel.heartRate.value)

}

}