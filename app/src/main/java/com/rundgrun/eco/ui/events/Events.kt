package com.rundgrun.eco.ui.events

import androidx.compose.foundation.background
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.rundgrun.eco.data.PakEvent

@Composable
fun EventLogView(
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = viewModel()
) {
    val gameUiState by viewModel.uiState.collectAsState()
    LazyColumn {
        items(gameUiState) {
            EventLogItem(event = it)
        }
    }
}

@Composable
fun EventLogItem(
    modifier: Modifier = Modifier,
    event: PakEvent
) {
    Row(modifier = Modifier.fillMaxWidth().background(color = Color.Black), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = event.date, fontSize = 8.sp, color = Color.Green)
        Text(text = event.name.toString(), fontSize = 10.sp, color = Color.Green)
        Text(text = event.volt.toString(), fontSize = 8.sp, color = Color.Green)
        Text(text = event.modem.toString(), fontSize = 8.sp, color = Color.Green)
        Text(text = event.airnode.toString(), fontSize = 8.sp, color = Color.Green)
    }
}
