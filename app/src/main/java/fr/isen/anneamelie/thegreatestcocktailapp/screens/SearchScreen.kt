package fr.isen.anneamelie.thegreatestcocktailapp.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.CocktailResponse
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.anneamelie.thegreatestcocktailapp.models.AppBarState
import fr.isen.anneamelie.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(contentPadding: PaddingValues, onComposing: (AppBarState) -> Unit) {
    var query by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Drink>?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onComposing(AppBarState("Search"))
    }

    LaunchedEffect(query) {
        if (query.length >= 2) {
            val call = ApiClient.retrofit.searchCocktails(query)
            call.enqueue(object : retrofit2.Callback<CocktailResponse> {
                override fun onResponse(
                    call: Call<CocktailResponse>,
                    response: Response<CocktailResponse>
                ) {
                    searchResults = response.body()?.drinks
                }

                override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                    // Handle error
                }
            })
        } else {
            searchResults = null
        }
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
        Column(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            TextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(28.dp)),
                placeholder = { Text("Search cocktails...", color = Color.White.copy(alpha = 0.5f)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.15f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                singleLine = true
            )

            LazyColumn(
                contentPadding = PaddingValues(
                    bottom = contentPadding.calculateBottomPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                searchResults?.let { list ->
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
                                .border(
                                    0.5.dp,
                                    Color.White.copy(alpha = 0.2f),
                                    RoundedCornerShape(16.dp)
                                ),
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
}
