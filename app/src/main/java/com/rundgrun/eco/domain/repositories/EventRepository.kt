package com.rundgrun.eco.domain.repositories

import com.rundgrun.eco.domain.models.Event

interface EventRepository {
    fun getEvents(): List<Event>
}