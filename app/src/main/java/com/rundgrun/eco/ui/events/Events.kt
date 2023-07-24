package com.rundgrun.eco.ui.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rundgrun.eco.domain.models.Event

@Composable
fun EventLogView(
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = viewModel()
) {
    val gameUiState by viewModel.uiState.collectAsState()
    LazyColumn {
        items(gameUiState) {
            Spacer(modifier = Modifier.height(6.dp))
            EventLogItem(event = it)
        }
    }
}

@Composable
fun EventLogItem(
    modifier: Modifier = Modifier,
    event: Event
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Black)) {
        Text(text = event.date,
            fontSize = 8.sp,
            color = Color.Green,
            modifier = Modifier.weight(0.2f),
            textAlign = TextAlign.Center)
        Text(text = event.deviceName.toString(),
            fontSize = 10.sp,
            color = Color.Green,
            modifier = Modifier.weight(0.2f),
            textAlign = TextAlign.Center)
        Text(text = event.volt.toString(),
            fontSize = 8.sp,
            color = Color.Red,
            modifier = Modifier.weight(0.2f),
            textAlign = TextAlign.Center)
        Text(text = event.modem.toString(),
            fontSize = 8.sp,
            color = Color.Green,
            modifier = Modifier.weight(0.2f),
            textAlign = TextAlign.Center)
        Text(text = event.airnode.toString(),
            fontSize = 8.sp,
            color = Color.Green,
            modifier = Modifier.weight(0.2f),
            textAlign = TextAlign.Center)
    }
}
