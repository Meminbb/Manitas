package com.example.manitas.navigation

sealed class ScreenNames (val route: String) {
    data object Categorias: ScreenNames("categorias")
    data object VideosporCat : ScreenNames("videosporcat/{id}?selectedId={selectedId}") {
        fun createRoute(id: Int, selectedId: Int = -1): String {
            return "videosporcat/$id?selectedId=$selectedId"
        }
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


    data object QuizList: ScreenNames("quizList")

    data object QuizQuestionbyCat : ScreenNames("quizQuestioncat/{id}/{name}") {
        fun createRoute(id: Int, name: String): String {
            return "quizQuestioncat/$id/$name"
        }
    }

}