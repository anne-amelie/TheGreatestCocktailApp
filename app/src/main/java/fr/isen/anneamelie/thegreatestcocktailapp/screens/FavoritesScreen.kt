package fr.isen.anneamelie.thegreatestcocktailapp.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil3.compose.AsyncImage
import fr.isen.anneamelie.thegreatestcocktailapp.DetailCocktailActivity
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.anneamelie.thegreatestcocktailapp.managers.FavoritesManager
import fr.isen.anneamelie.thegreatestcocktailapp.models.AppBarState

@Composable
fun FavoritesScreen(modifier: Modifier, onComposing: (AppBarState) -> Unit) {
    val context = LocalContext.current
    val favoritesManager = FavoritesManager()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifeCycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    var favorites = remember {
        mutableStateOf<List<Drink>>(favoritesManager.getFavorites(context))
    }

    LaunchedEffect(lifeCycleState) {
        onComposing(
            AppBarState("Favorites")
        )

        when(lifeCycleState) {
            Lifecycle.State.RESUMED -> {
                favorites.value = favoritesManager.getFavorites(context)
            }
            else -> { }
        }
    }
    LazyColumn(modifier) {
        items(favorites.value) { item ->
            Card(Modifier.clickable {
                val intent = Intent(context, DetailCocktailActivity::class.java)
                intent.putExtra(DetailCocktailActivity.DRINKID, item.idDrink)
                context.startActivity(intent)
            }) {
                Row {
                    AsyncImage(
                        model = item.strDrinkThumb,
                        "",
                        Modifier.width(80.dp)
                            .height(80.dp)
                            .clip(CircleShape)
                    )
                    Text(item.strDrink ?: "")
                }
            }
        }
    }
}