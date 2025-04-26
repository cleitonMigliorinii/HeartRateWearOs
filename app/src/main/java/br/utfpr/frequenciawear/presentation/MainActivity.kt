/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package br.utfpr.frequenciawear.presentation

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.wear.compose.material.MaterialTheme

class MainActivity : ComponentActivity() {

    //Gerenciado de sensores
    private lateinit var sensorManager: SensorManager

    private var heartRateSensor: Sensor? = null

    private var sensorListener: SensorEventListener? = null

    private val heartRateViewModel = HeartRateViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        //Pedir permissão
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.BODY_SENSORS)
            != PackageManager.PERMISSION_GRANTED
            ){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.BODY_SENSORS), 1)
        }

        //Inicializar os sensores
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)

        setContent {

            //Coleta o estado atual do batimento do ViewMode
            val heartRateState by remember { heartRateViewModel.heartRate }

            //Efeito de criação executado apenas uma vez na primeira composição
            LaunchedEffect(Unit) {

                val listener = object : SensorEventListener {
                    override fun onSensorChanged(event: SensorEvent?) {
                        event?.values?.firstOrNull()?.let { value -> heartRateViewModel.updateHeartRate(value) }
                    }

                    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                }

                sensorListener = listener

                //Registrando o nosso listener do nosso sensor
                heartRateSensor?.let{
                    sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL)
                }

            }

            //Quando sair de cena, remove o listener
            DisposableEffect(Unit) {
                onDispose {
                    sensorListener?.let{
                        sensorManager.unregisterListener(it)
                    }
                }
            }

            MaterialTheme{
                HeartRateScreen(heartRate = heartRateState)
            }

        }
    }
}