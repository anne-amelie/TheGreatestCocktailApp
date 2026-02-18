package fr.isen.anneamelie.thegreatestcocktailapp.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.anneamelie.thegreatestcocktailapp.DrinksActivity

@Composable
fun CategoriesScreen(modifier: Modifier) {
    val list = listOf("Beer", "Cocktail", "Coffee")
    val context = LocalContext.current
    LazyColumn(modifier
        .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(list) { category ->
            Card(Modifier.clickable {
                val intent = Intent(context, DrinksActivity::class.java)
                context.startActivity(intent)
            }) {
                Text("${category}",
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}