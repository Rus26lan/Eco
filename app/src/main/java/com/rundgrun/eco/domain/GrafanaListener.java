package com.rundgrun.eco.domain;



import com.rundgrun.eco.domain.models.Pak;
import com.rundgrun.eco.domain.models.Event;

import java.util.ArrayList;

public interface GrafanaListener {
    void updateEventLog(ArrayList<Event> list);
    void updateProblemPAK(ArrayList<Event> list);
    void updateAllPAK(ArrayList<Pak> list);
}
