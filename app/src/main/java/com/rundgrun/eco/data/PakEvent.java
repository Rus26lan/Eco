package com.rundgrun.eco.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PakEvent {
    static DateFormat dateFormat = new SimpleDateFormat("dd MMM yy   HH:mm:ss");
    public String date;
    public long mls;
    public int name;
    public StatusPak volt;
    public StatusPak modem;
    public StatusPak airnode;

    public PakEvent(String mls, String name, String volt, String modem, String airnode) {
        this.mls = Long.parseLong(mls);
        this.date = dateFormat.format(new Date(this.mls));
        this.name = Integer.parseInt(name.replace("AN", ""));
        this.volt = StatusPak.getStatus(volt);
        this.modem = StatusPak.getStatus(modem);
        this.airnode = StatusPak.getStatus(airnode);
    }

    @Override
    public String toString() {
        return "date = " + date + '\'' +
                ", name = " + name +
                ", volt = " + volt +
                ", modem = " + modem +
                ", airnode = " + airnode;
    }
}
