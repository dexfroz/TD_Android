package fr.isen.andre.androidtoolbox.models

class LocationModel {
    var city: String? = null
    var state: String? = null
    var country: String? = null
    var street: StreetModel? = null
}

class StreetModel {
    var number: Int? = null
    var name: String? = null
}