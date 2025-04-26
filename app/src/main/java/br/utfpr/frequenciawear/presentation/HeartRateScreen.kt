package br.utfpr.frequenciawear.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.wear.compose.material.Text

@Composable
fun HeartRateScreen(heartRate : Int) {

    val (backgroundColor, statusText) = when {
        heartRate <= 100 -> Pair(Color(0xFF1B5E20), "✅ Normal")
        heartRate in 101..120 -> Pair(Color(0xFFFFA000), "⚠️ Frequência Alterada")
        else -> Pair(Color.Red, "🚨 Frequência Elevada!")
    }

  val animateColor by animateColorAsState(
      targetValue = backgroundColor,
      label = "bgColor"
  )

  Surface(
      modifier = Modifier
          .fillMaxSize().background(animateColor),
      color = animateColor
  ) {

      Box(
          modifier = Modifier.fillMaxSize().padding(16.dp),
          contentAlignment = Alignment.Center
      ) {

          Column(horizontalAlignment = Alignment.CenterHorizontally) {

              Text(
                  text = "Frequência Cardíaca",
                  style = MaterialTheme.typography.titleMedium,
                  color = Color.White
              )
              Spacer(modifier = Modifier.height(10.dp))

              Text(
                  text = "$heartRate bpm",
                  style = MaterialTheme.typography.displayMedium,
                  color = Color.White
              )
              Spacer(modifier = Modifier.height(10.dp))

              Text(
                  text = statusText,
                  style = MaterialTheme.typography.bodyMedium,
                  color = Color.White
              )

          }

      }

  }

}