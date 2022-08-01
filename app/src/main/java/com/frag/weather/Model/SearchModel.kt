package com.frag.weather.Model


data class  SearchModel(
    val coord : Coord,
    val weather : List<Weather>,
    val main : Main,
    val wind : Wind,
    val clouds : Clouds,
    val dt : Long,
    val sys : Sys,
    val timezone : Long,
    val id :Long,
    val name : String,
    val cod : Int
)