package com.example.manitas.navigation

sealed class ScreenNames (val route: String) {
    data object Categorias: ScreenNames("categorias")
    data object VideosporCat: ScreenNames("videosporcat/{id}") {
        fun createRoute(id: Int) = "videosporcat/$id"
    }
    data object NotificacionesAdd: ScreenNames("notificaciones_add")

    data object LoginScreen: ScreenNames("login")
    data object LoginUser: ScreenNames("loginuser")
    data object SessionScreen: ScreenNames("session")
    data object CreateUserScreen: ScreenNames("createuser")

    data object Menu: ScreenNames("menu")
    data object Progreso: ScreenNames("progreso")
    data object Notificaciones: ScreenNames("notificaciones")
    data object Favoritos: ScreenNames("favoritos")

    data object FavoritoDetalle : ScreenNames("favoritoDetalle/{id}") {
        fun createRoute(id: Int) = "favoritoDetalle/$id"
    }
    data object Quiz : ScreenNames("quiz")
    data object SeñaDetail : ScreenNames("seña_detail/{id}") {
        fun createRoute(id: String) = "seña_detail/$id"
    }
}