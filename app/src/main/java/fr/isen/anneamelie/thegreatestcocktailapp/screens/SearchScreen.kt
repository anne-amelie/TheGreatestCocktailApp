package fr.isen.anneamelie.thegreatestcocktailapp.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import fr.isen.anneamelie.thegreatestcocktailapp.DetailCocktailActivity
import fr.isen.anneamelie.thegreatestcocktailapp.R
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.CocktailResponse
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.DrinkFilterResponse
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.DrinkPreview
import fr.isen.anneamelie.thegreatestcocktailapp.models.AppBarState
import fr.isen.anneamelie.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response

enum class SearchType { NAME, INGREDIENT }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(contentPadding: PaddingValues, onComposing: (AppBarState) -> Unit) {
    var query by remember { mutableStateOf("") }
    var searchType by remember { mutableStateOf(SearchType.NAME) }
    var searchResults by remember { mutableStateOf<List<DrinkPreview>?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onComposing(AppBarState("Search"))
    }

    LaunchedEffect(query, searchType) {
        if (query.length >= 2) {
            if (searchType == SearchType.NAME) {
                val call = ApiClient.retrofit.searchCocktails(query)
                call.enqueue(object : retrofit2.Callback<CocktailResponse> {
                    override fun onResponse(
                        call: Call<CocktailResponse>,
                        response: Response<CocktailResponse>
                    ) {
                        searchResults = response.body()?.drinks?.map {
                            DrinkPreview(it.idDrink, it.strDrink, it.strDrinkThumb)
                        }
                    }

                    override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                        searchResults = null
                    }
                })
            } else {
                val call = ApiClient.retrofit.searchByIngredient(query)
                call.enqueue(object : retrofit2.Callback<DrinkFilterResponse> {
                    override fun onResponse(
                        call: Call<DrinkFilterResponse>,
                        response: Response<DrinkFilterResponse>
                    ) {
                        searchResults = response.body()?.drinks
                    }

                    override fun onFailure(call: Call<DrinkFilterResponse>, t: Throwable) {
                        searchResults = null
                    }
                })
            }
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
                placeholder = {
                    Text(
                        if (searchType == SearchType.NAME) "Search by name..." else "Search by ingredient...",
                        color = Color.White.copy(alpha = 0.5f)
                    )
                },
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SearchTypeButton(
                    text = "Name",
                    isSelected = searchType == SearchType.NAME,
                    onClick = { searchType = SearchType.NAME }
                )
                SearchTypeButton(
                    text = "Ingredient",
                    isSelected = searchType == SearchType.INGREDIENT,
                    onClick = { searchType = SearchType.INGREDIENT }
                )
            }

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
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(12.dp))
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

@Composable
fun SearchTypeButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Color.White.copy(alpha = 0.25f) else Color.Transparent)
            .clickable(
                interactionSource = interactionSource,
                indication = null // Removes the circular ripple on click
            ) { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
