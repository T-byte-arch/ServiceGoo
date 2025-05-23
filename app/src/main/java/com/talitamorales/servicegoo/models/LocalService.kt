package com.talitamorales.servicegoo.models

import com.google.android.gms.maps.model.LatLng

data class LocalService(
    val name: String,
    val price: String,
    val latLng: LatLng
)
