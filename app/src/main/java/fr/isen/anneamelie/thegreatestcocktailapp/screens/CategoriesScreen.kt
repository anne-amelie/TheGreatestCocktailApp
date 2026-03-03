package fr.isen.anneamelie.thegreatestcocktailapp.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.anneamelie.thegreatestcocktailapp.DrinksActivity
import fr.isen.anneamelie.thegreatestcocktailapp.R
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.CategoryListResponse
import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.DrinkCategory
import fr.isen.anneamelie.thegreatestcocktailapp.models.AppBarState
import fr.isen.anneamelie.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response

@Composable
fun CategoriesScreen(contentPadding: PaddingValues, onComposing: (AppBarState) -> Unit) {
    val context = LocalContext.current
    var categories = remember { mutableStateOf<List<DrinkCategory>?>(null) }

    LaunchedEffect(Unit) {
        onComposing(
            AppBarState("Categories")
        )
        val call = ApiClient.retrofit.getCategories()
        call.enqueue(object : retrofit2.Callback<CategoryListResponse> {
            override fun onResponse(
                call: Call<CategoryListResponse>,
                response: Response<CategoryListResponse>
            ) {
                categories.value = response?.body()?.drinks
            }

            override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                Log.e("request", "getcategories failed ${t?.message}")
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
        categories.value?.let { list ->
            LazyColumn(
                contentPadding = PaddingValues(
                    top = contentPadding.calculateTopPadding() + 16.dp,
                    bottom = contentPadding.calculateBottomPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(list) { category ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                val intent = Intent(context, DrinksActivity::class.java)
                                intent.putExtra(DrinksActivity.CATEGORY, category.strCategory)
                                context.startActivity(intent)
                            }
                            .border(0.5.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.1f)
                        )
                    ) {
                        Text(
                            text = category.strCategory ?: "",
                            modifier = Modifier.padding(16.dp),
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}
