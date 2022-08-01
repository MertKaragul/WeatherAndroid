package com.frag.weather.Model

data class WeatherModel(
    val coord: Coord,
    val weather: List<Weather>,
    val base : String?,
    val main: Main,
    val visibility : Long?,
    val wind: Wind,
    val clouds: Clouds,
    val dt : Long?,
    val sys: Sys,
    val timezone : Long?,
    val id : Long?,
    val name : String?,
    val cod : Int?
)

data class Main(
    val temp : Double?,
    val feels_like : Double?,
    val temp_min : Double?,
    val temp_max : Double?,
    val pressure : Int?,
    val humidity : Int?,
)

data class  Weather(
    val id : Int,
    val main : String,
    val description : String,
    val icon : String
)

data class Coord(
    val lon : String?,
    val lat : String?
)
data class Wind(
    val speed : Double?,
    val deg : Int?,
)

data class Clouds(
    val all : Int?
)

data class Sys(
    val type : Int?,
    val id : Int?,
    val country : String?,
    val sunrise : Long?,
    val sunset : Long?
)