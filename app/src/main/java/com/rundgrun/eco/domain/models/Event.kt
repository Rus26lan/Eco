package com.rundgrun.eco.domain.models

data class Event(
    var mls: Long,
    var deviceName: Int,
    var volt: StatusPak,
    var modem: StatusPak,
    var airnode: StatusPak
) {

//        this.mls = mls.toLong()
//        date = dateFormat.format(Date(this.mls))
//        this.name = name.replace("AN", "").toInt()
//    this.volt = StatusPak.getStatus(volt)
//    this.modem = StatusPak.getStatus(modem)
//    this.airnode = StatusPak.getStatus(airnode)

//    companion object {
//        var dateFormat: DateFormat = SimpleDateFormat("dd MMM yy   HH:mm:ss")
//    }
}