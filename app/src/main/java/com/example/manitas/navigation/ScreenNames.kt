package com.example.manitas.navigation

sealed class ScreenNames (val route: String) {
    data object Categorias: ScreenNames("categorias")
    data object VideosporCat: ScreenNames("videosporcat/{id}") {
        fun createRoute(id: Int) = "videosporcat/$id"
    }

}