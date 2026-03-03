package fr.isen.anneamelie.thegreatestcocktailapp.screens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import fr.isen.anneamelie.thegreatestcocktailapp.R
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.CocktailResponse
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.anneamelie.thegreatestcocktailapp.managers.FavoritesManager
import fr.isen.anneamelie.thegreatestcocktailapp.models.AppBarState
import fr.isen.anneamelie.thegreatestcocktailapp.models.Category
import fr.isen.anneamelie.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response

@Composable
fun RandomCocktailScreen(contentPadding: PaddingValues, onComposing: (AppBarState) -> Unit) {
    var drink = remember { mutableStateOf<Drink?>(null) }
    var refreshCount = remember { mutableStateOf(0) }

    fun fetchRandomDrink() {
        val call = ApiClient.retrofit.getRandomCocktail()
        call.enqueue(object : retrofit2.Callback<CocktailResponse> {
            override fun onResponse(
                call: Call<CocktailResponse?>?,
                response: Response<CocktailResponse?>?
            ) {
                drink.value = response?.body()?.drinks?.first()
            }
            override fun onFailure(
                call: Call<CocktailResponse?>?,
                t: Throwable?
            ) {
                Log.e("request", "getrandom failed ${t?.message}")
            }
        })
    }

    LaunchedEffect(refreshCount.value) {
        onComposing (
            AppBarState("Random Cocktail",
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RefreshTopButton {
                            refreshCount.value++
                        }
                        DetailCocktailTopButton(drink.value)
                    }
                })
        )
        fetchRandomDrink()
    }

    drink.value?.let { drink ->
        DetailCocktailScreen(contentPadding, drink)
    } ?: run {
        Text("Loading", color = Color.White, modifier = Modifier.padding(contentPadding).padding(16.dp))
    }
}

@Composable
fun DetailCocktailScreen(drinkId: String,
                         onComposing: (AppBarState) -> Unit,
                         contentPadding: PaddingValues) {
    var drink = remember { mutableStateOf<Drink?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {

        onComposing (
            AppBarState(
                title = "Cocktail Detail",
                actions = { DetailCocktailTopButton(drink.value) },
                onBackClick = { (context as? Activity)?.finish() }
            )
        )
        val call = ApiClient.retrofit.getDetailCocktail(drinkId)
        call.enqueue(object : retrofit2.Callback<CocktailResponse> {
            override fun onResponse(
                call: Call<CocktailResponse?>?,
                response: Response<CocktailResponse?>?
            ) {
                drink.value = response?.body()?.drinks?.first()
                drink.value?.let {
                    onComposing(
                        AppBarState(
                            title = it.strDrink ?: "Cocktail Detail",
                            actions = { DetailCocktailTopButton(drink.value) },
                            onBackClick = { (context as? Activity)?.finish() }
                        )
                    )
                }
            }
            override fun onFailure(
                call: Call<CocktailResponse?>?,
                t: Throwable?
            ) {
                Log.e("request", "getdetail failed ${t?.message}")
            }
        })
    }

    drink.value?.let { drink ->
        DetailCocktailScreen(contentPadding, drink)
    } ?: run {
        Text("Loading", color = Color.White, modifier = Modifier.padding(contentPadding).padding(16.dp))
    }
}

@Composable
fun DetailCocktailScreen(contentPadding: PaddingValues, drink: Drink) {
    Box(
        Modifier.background(
            brush = Brush.verticalGradient(
                listOf(
                    colorResource(R.color.purple_500),
                    colorResource(R.color.purple_700)
                )
            ))
            .fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(rememberScrollState())
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            
            // Add spacer for TopAppBar
            Spacer(Modifier.height(contentPadding.calculateTopPadding()))
            
            AsyncImage(
                model = drink.strDrinkThumb,
                "",
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .clip(CircleShape)
                    .border(
                        1.dp,
                        colorResource(R.color.teal_200),
                        CircleShape
                    )
            )

            Text(drink.strDrink ?: "",
                fontSize = 40.sp,
                color = colorResource(R.color.white),
                fontWeight = FontWeight.Bold)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                CategoryView(Category.OTHER)
                CategoryView(Category.NON_ALCOHOLIC)
            }
            Text(
                "Cocktail glass",
                color = colorResource(R.color.grey)
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                modifier = Modifier.padding(horizontal = 16.dp).border(0.5.dp, Color.White.copy(alpha = 0.2f), CardDefaults.shape)
            ) {
                Column(
                    Modifier.padding(16.dp)
                        .fillMaxWidth()) {
                    Text(stringResource(R.string.igrendient),
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold)
                    Text("Coca-cola", color = Color.White)
                }
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                modifier = Modifier.padding(horizontal = 16.dp).border(0.5.dp, Color.White.copy(alpha = 0.2f), CardDefaults.shape)
            ) {
                Column(
                    Modifier.padding(16.dp)
                        .fillMaxWidth()) {
                    Text(stringResource(R.string.preparation),
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold)
                    Text(drink.strInstructions ?: "No instructions available.", color = Color.White)
                }
            }
            
            // Add spacer for BottomAppBar
            Spacer(Modifier.height(contentPadding.calculateBottomPadding()))
        }
    }
}

@Composable
fun RefreshTopButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.15f))
            .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Refresh",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun DetailCocktailTopButton(drink: Drink?) {
    val context = LocalContext.current
    val favoritesManager = FavoritesManager()
    drink?.let { drink ->
        var isFavorites = remember {
            mutableStateOf<Boolean>(favoritesManager.isFavorite(drink, context))
        }

        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
                .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                .clickable {
                    favoritesManager.toggleFavorite(drink, context)
                    isFavorites.value = favoritesManager.isFavorite(drink, context)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isFavorites.value) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Filled.FavoriteBorder
                },
                contentDescription = "Favorite",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun CategoryView(catogory: Category) {
    Box(Modifier
        .clip(CircleShape)
        .background(
            Brush.horizontalGradient(
                Category.colors(catogory)
            )
        )
    ) {
        Text(
            Category.toString(catogory),
            fontSize = 20.sp,
            color = colorResource(R.color.white),
            modifier = Modifier.padding(8.dp)
        )
    }
}