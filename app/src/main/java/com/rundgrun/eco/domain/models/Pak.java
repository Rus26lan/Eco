package com.rundgrun.eco.domain.models;

public class Pak {
    public int name;
    public StatusPak volt;
    public StatusPak modem;
    public StatusPak airnode;
    public String description;

    public Pak(String name, String volt, String modem, String airnode, String description) {
        this.name = Integer.parseInt(name.replace("AN", ""));
        this.volt = StatusPak.getStatus(volt);
        this.modem = StatusPak.getStatus(modem);
        this.airnode = StatusPak.getStatus(airnode);
        this.description = description;
    }

    @Override
    public String toString() {
        return "name = " + name +
                ", volt = " + volt +
                ", modem = " + modem +
                ", airnode = " + airnode +
                ", description = " + description;
    }
}
