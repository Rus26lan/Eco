package com.rundgrun.eco.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rundgrun.eco.domain.models.Pak
import com.rundgrun.eco.domain.models.PakEvent
import com.rundgrun.eco.domain.GrafanaClient
import com.rundgrun.eco.domain.GrafanaListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class EventsViewModel : ViewModel() {
    private val client = GrafanaClient()
    var events = java.util.ArrayList<PakEvent>()
    private val _uiState = MutableStateFlow(ArrayList<PakEvent>())
    val uiState: StateFlow<ArrayList<PakEvent>> = _uiState.asStateFlow()

    init {
        for (i in 1..100) {
            val event = PakEvent(
                Date().time.toString(),
                Random().nextInt(200).toString(), "5", "5", "5"
            )
            events.add(event)
        }
        _uiState.value = events
        viewModelScope.launch(Dispatchers.IO) {
            client.setListener(object : GrafanaListener {
                override fun updateEventLog(list: ArrayList<PakEvent>?) {
                    _uiState.value = list!!
                }

                override fun updateProblemPAK(list: ArrayList<PakEvent>?) {

                }

                override fun updateAllPAK(list: ArrayList<Pak>?) {

                }
            })
            delay(10000)
//            client.singleConnection()
        }
    }
}