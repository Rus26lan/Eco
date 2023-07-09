package com.rundgrun.eco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rundgrun.eco.data.PakEvent
import com.rundgrun.eco.ui.theme.EcoTheme
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var events = ArrayList<PakEvent>()
        for(i in 1..100){
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
                    EventLog(events)
                }
            }
        }
    }
    @Composable
    fun EventLog(events: ArrayList<PakEvent>) {
        LazyColumn {
            items(events) {
                EventLogItem(event = it)
            }
        }
    }

    @Composable
    fun EventLogItem(
        modifier: Modifier = Modifier,
        event: PakEvent
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
        Arrangement.SpaceBetween) {
            Text( text = event.date,fontSize = 8.sp)
            Text(text = event.name.toString(),fontSize = 8.sp)
            Text(text = event.volt.toString(),fontSize = 8.sp)
            Text(text = event.modem.toString(),fontSize = 8.sp)
            Text(text = event.airnode.toString(),fontSize = 8.sp)
        }
    }

}