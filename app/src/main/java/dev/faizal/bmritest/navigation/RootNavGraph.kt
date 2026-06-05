package dev.faizal.bmritest.navigation

import androidx.compose.runtime.Composable
import dev.faizal.bmritest.ui.screen.MovieDetailScreen
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.faizal.bmritest.ui.screen.GenreScreen
import dev.faizal.bmritest.ui.screen.MovieScreen

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Genre,
        modifier = modifier
    ) {

        composable<Screen.Genre> {
            GenreScreen(
                onGenreClick = { genreId, genreName ->
                    navController.navigate(Screen.Movie(genreId, genreName))
                }
            )
        }
        composable<Screen.Movie> {  backStackEntry ->
            val args = backStackEntry.toRoute<Screen.Movie>()
            MovieScreen(
                genreName = args.genreName,
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail(movieId))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable<Screen.Detail> {
            MovieDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}