package fr.isen.anneamelie.thegreatestcocktailapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.anneamelie.thegreatestcocktailapp.models.AppBarState
import fr.isen.anneamelie.thegreatestcocktailapp.screens.BottomAppBar
import fr.isen.anneamelie.thegreatestcocktailapp.screens.CategoriesScreen
import fr.isen.anneamelie.thegreatestcocktailapp.screens.RandomCocktailScreen
import fr.isen.anneamelie.thegreatestcocktailapp.screens.FavoritesScreen
import fr.isen.anneamelie.thegreatestcocktailapp.screens.SearchScreen
import fr.isen.anneamelie.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Log.d("LifeCycle", "MainActivity onCreate")
        setContent {
            val navController = rememberNavController()
            val appBarState = remember { mutableStateOf(AppBarState()) }

            val randomItem = TabBarItem(
                stringResource(R.string.tab_item_random),
                Icons.Filled.Home,
                Icons.Outlined.Home
            )
            val categoryItem = TabBarItem(
                stringResource(R.string.tab_item_category),
                Icons.Filled.Menu,
                Icons.Outlined.Menu
            )
            val favoriteItem = TabBarItem(
                stringResource(R.string.tab_item_favorite),
                Icons.Filled.Favorite,
                Icons.Outlined.Favorite
            )
            val tabItems = listOf(randomItem, categoryItem, favoriteItem)

            TheGreatestCocktailAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { },
                            navigationIcon = {
                                appBarState.value.onBackClick?.let {
                                    IconButton(onClick = it) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back",
                                            tint = Color.White
                                        )
                                    }
                                }
                            },
                            actions = { appBarState.value.actions?.invoke(this) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent
                            )
                        )
                    },
                    bottomBar = { BottomAppBar(tabItems, navController) }
                ) { innerPadding ->
                    // Do not apply innerPadding to the container to allow content to go behind transparent bars
                    Box(Modifier.fillMaxSize()) {
                        NavHost(navController, startDestination = randomItem.title) {
                            composable(randomItem.title) {
                                RandomCocktailScreen(
                                    contentPadding = innerPadding,
                                    onComposing = { topBar -> appBarState.value = topBar }
                                )
                            }
                            composable(categoryItem.title) {
                                CategoriesScreen(
                                    contentPadding = innerPadding,
                                    onComposing = { topBar -> appBarState.value = topBar }
                                )
                            }
                            composable(favoriteItem.title) {
                                FavoritesScreen(
                                    contentPadding = innerPadding,
                                    onComposing = { topBar -> appBarState.value = topBar }
                                )
                            }
                            composable("search") {
                                SearchScreen(
                                    contentPadding = innerPadding,
                                    onComposing = { topBar -> appBarState.value = topBar }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}