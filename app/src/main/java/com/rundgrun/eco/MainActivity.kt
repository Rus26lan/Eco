package com.rundgrun.eco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.rundgrun.eco.data.PakEvent
import com.rundgrun.eco.ui.events.EventLogView
import com.rundgrun.eco.ui.theme.EcoTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var events = ArrayList<PakEvent>()
        for (i in 1..100) {
            val event = PakEvent(
                Date().time.toString(),
                Random().nextInt(200).toString(), "5", "5", "5"
            )
            events.add(event)
        }
        setContent {
            EcoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EventLogView()
                }
            }
        }
    }
}