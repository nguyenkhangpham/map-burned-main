package com.canhbbaochayrung

data class FirePoint(
    val name: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val brightTi4: Double = 0.0,
    val scan: Double = 0.0,
    val track: Double = 0.0,
    val acqDate: String = "",
    val acqTime: Long = 0L,
    val satellite: String = "",
    val instrument: String = "",
    val confidence: String = "",
    val version: String = "",
    val brightTi5: Double = 0.0,
    val frp: Double = 0.0,
    val daynight: String = "",
){
    constructor(values: List<String>) : this(
        name = values.getOrElse(0) { "" },
        lat = values.getOrElse(1) { "0.0" }.toDouble(),
        lng = values.getOrElse(2) { "0.0"}.toDouble(),
        brightTi4 = values.getOrElse(3) { "0.0" }.toDouble(),
        scan = values.getOrElse(4) { "0.0" }.toDouble(),
        track = values.getOrElse(5) { "0.0" }.toDouble(),
        acqDate = values.getOrElse(6) { "" },
        acqTime = values.getOrElse(7) { "0" }.toLong(),
        satellite = values.getOrElse(8) { "" },
        instrument = values.getOrElse(9) { "" } ,
        confidence = values.getOrElse(10) { "" },
        version = values.getOrElse(11) { "" },
        brightTi5 = values.getOrElse(12) { "0.0" }.toDouble(),
        frp = values.getOrElse(13) { "0.0" }.toDouble(),
        daynight = values.getOrElse(14) { "" },

    )
}