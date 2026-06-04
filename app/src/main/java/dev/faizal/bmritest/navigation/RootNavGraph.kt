package dev.faizal.bmritest.navigation

import androidx.compose.runtime.Composable
import dev.faizal.bmritest.ui.screen.HomeScreen
import dev.faizal.bmritest.ui.screen.MovieDetailScreen
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        modifier = modifier
    ) {
        composable<Screen.Home> {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail(movieId))
                }
            )
        }
        composable<Screen.Detail> {
            MovieDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}