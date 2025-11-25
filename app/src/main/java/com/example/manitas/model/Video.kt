package com.example.manitas.model

class Video (
    val id: Int,
    val catId: Int,
    val name: String,
<<<<<<< Updated upstream
    val url: String,
=======
    val resId: Int,
    val type: MediaType,
    var fav: Boolean = false
>>>>>>> Stashed changes
)

fun getVideos(): List<Video> = listOf(
    Video(1,1,"Manzana", "link o video de manzana"),
    Video(2,1,"Naranja","link o video de narnja"),
    Video(3,1,"Banano","link o video de banano"),
    Video(4,2,"A", "link o video de A"),

)