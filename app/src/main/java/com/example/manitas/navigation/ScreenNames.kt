package com.example.manitas.navigation

sealed class ScreenNames (val route: String) {
    data object Categorias: ScreenNames("categorias")
    data object VideosporCat: ScreenNames("videosporcat/{id}") {
        fun createRoute(id: Int) = "videosporcat/$id"
    }

    data object Menu: ScreenNames("menu")
    data object Progreso: ScreenNames("progreso")
    data object Notificaciones: ScreenNames("notificaciones")
    data object Favoritos: ScreenNames("favoritos")

    data object FavoritoDetalle : ScreenNames("favoritoDetalle/{id}") {
        fun createRoute(id: Int) = "favoritoDetalle/$id"
    }

}