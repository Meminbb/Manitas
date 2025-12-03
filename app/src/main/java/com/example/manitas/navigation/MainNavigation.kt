package com.example.manitas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.manitas.screens.menu.MenuScreen
import com.example.manitas.model.getCategories
import com.example.manitas.model.getVideos
import com.example.manitas.screens.CategoriesScreen
import com.example.manitas.screens.VideosporCatScreen
import com.example.manitas.screens.favoritos.FavoritosScreen
import com.example.manitas.screens.favoritos.VideosFavoritosScreen
import com.example.manitas.screens.login.LoginScreen
import com.example.manitas.screens.login.LoginUserScreen
import com.example.manitas.screens.login.SessionScreen
import com.example.manitas.screens.login.CreateUserScreen
import com.example.manitas.screens.progreso.ProgresoScreen
import com.example.manitas.screens.notificaciones.NotificacionesScreen
import com.example.manitas.screens.notificaciones.NotificacionesAddScreen
import com.example.manitas.screens.quiz.QuizCategoriesScreen
import com.example.manitas.screens.quiz.QuizScreen


@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = ScreenNames.LoginScreen.route,
        modifier = modifier
    ) {

        composable(ScreenNames.Menu.route) {
            MenuScreen(
                onNavigate = { route -> nav.navigate(route) }
            )
        }
        composable(ScreenNames.NotificacionesAdd.route) {
            NotificacionesAddScreen(nav = nav)
        }

        composable(ScreenNames.Categorias.route) {
            val categories = getCategories()

            CategoriesScreen(
                categories = categories,
                nav = nav,
                onItemClick = { category ->
                    nav.navigate(ScreenNames.VideosporCat.createRoute(category.id))
                }
            )
        }
        composable("notificacionesAdd") {
            NotificacionesAddScreen(nav = nav)
        }


        composable(ScreenNames.Progreso.route) {
            ProgresoScreen(nav = nav)
        }

        composable(ScreenNames.Notificaciones.route) {
            NotificacionesScreen(nav = nav)
        }

        composable(ScreenNames.Favoritos.route) {
            FavoritosScreen(nav = nav)
        }

        composable(ScreenNames.LoginScreen.route) {
            LoginScreen(nav = nav)
        }
        composable(ScreenNames.LoginUser.route) {
            LoginUserScreen(nav = nav)
        }
        composable(ScreenNames.SessionScreen.route){
            SessionScreen(nav = nav)
        }
        composable(ScreenNames.CreateUserScreen.route){
            CreateUserScreen(nav = nav)
        }

        composable(
            route = ScreenNames.FavoritoDetalle.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            VideosFavoritosScreen(
                nav = nav,
                favoritoId = id
            )
        }


        composable(
            route = ScreenNames.VideosporCat.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val videos = getVideos().filter { it.catId == id }

            VideosporCatScreen(
                idCategory = id,
                videos = videos
            )
        }


        //composable(ScreenNames.Quiz.route) {
            //QuizScreen()
        //}

        composable(ScreenNames.QuizList.route) {
            val categories = getCategories()
            QuizCategoriesScreen(
                categories = categories,
                nav = nav,
                onItemClick = { category ->
                    nav.navigate(ScreenNames.QuizQuestionbyCat.createRoute(category.id))
                }
            )
        }

        composable(
            route = ScreenNames.VideosporCat.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("selectedId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val selectedId = backStackEntry.arguments?.getInt("selectedId") ?: -1

            val videos = getVideos().filter { it.catId == id }

            VideosporCatScreen(
                idCategory = id,
                videos = videos,
                nav = nav,
                selectedVideoId = if (selectedId != -1) selectedId else null
            )
        }
    }
}



