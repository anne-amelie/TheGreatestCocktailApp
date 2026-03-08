package fr.isen.anneamelie.thegreatestcocktailapp.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import fr.isen.anneamelie.thegreatestcocktailapp.DetailCocktailActivity
import fr.isen.anneamelie.thegreatestcocktailapp.R
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.DrinkFilterResponse
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.DrinkPreview
import fr.isen.anneamelie.thegreatestcocktailapp.models.AppBarState
import fr.isen.anneamelie.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response

@Composable
fun DrinksScreen(contentPadding: PaddingValues, category: String, onComposing: (AppBarState) -> Unit) {

    var drinks = remember { mutableStateOf<List<DrinkPreview>?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onComposing(
            AppBarState(
                title = category,
                onBackClick = { (context as? Activity)?.finish() }
            )
        )
        val call = ApiClient.retrofit.getDrinksPreview(category)
        call.enqueue(object : retrofit2.Callback<DrinkFilterResponse> {
            override fun onResponse(
                call: Call<DrinkFilterResponse?>?,
                response: Response<DrinkFilterResponse?>?
            ) {
                drinks.value = response?.body()?.drinks
            }

            override fun onFailure(
                call: Call<DrinkFilterResponse?>?,
                t: Throwable?
            ) {
                // Handle error
            }
        })
    }

    Box(
        Modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        colorResource(R.color.purple_500),
                        colorResource(R.color.purple_700)
                    )
                )
            )
            .fillMaxSize()
    ) {
        drinks.value?.let { list ->
            LazyColumn(
                contentPadding = PaddingValues(
                    top = contentPadding.calculateTopPadding() + 16.dp,
                    bottom = contentPadding.calculateBottomPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(list) { drink ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                val intent = Intent(context, DetailCocktailActivity::class.java)
                                intent.putExtra(DetailCocktailActivity.DRINKID, drink.idDrink)
                                context.startActivity(intent)
                            }
                            .border(0.5.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.1f)
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = drink.strDrinkThumb,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(60.dp)
                                    .height(60.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = drink.strDrink ?: "",
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
