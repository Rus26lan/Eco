package com.rundgrun.eco.domain;



import com.rundgrun.eco.data.Pak;
import com.rundgrun.eco.data.PakEvent;

import java.util.ArrayList;

public interface GrafanaListener {
    void updateEventLog(ArrayList<PakEvent> list);
    void updateProblemPAK(ArrayList<PakEvent> list);
    void updateAllPAK(ArrayList<Pak> list);
}
