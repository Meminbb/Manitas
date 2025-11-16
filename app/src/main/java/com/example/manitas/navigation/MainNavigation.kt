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
import com.example.manitas.screens.progreso.ProgresoScreen
import com.example.manitas.screens.notificaciones.NotificacionesScreen

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = ScreenNames.Menu.route,
        modifier = modifier
    ) {

        composable(ScreenNames.Menu.route) {
            MenuScreen(
                onNavigate = { route -> nav.navigate(route) }
            )
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


        composable(ScreenNames.Progreso.route) {
            ProgresoScreen(nav = nav)
        }

        composable(ScreenNames.Notificaciones.route) {
            NotificacionesScreen(nav = nav)
        }

        composable(ScreenNames.Favoritos.route) {
            FavoritosScreen(nav = nav)
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
    }
}
