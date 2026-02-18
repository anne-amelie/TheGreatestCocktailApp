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
import fr.isen.anneamelie.thegreatestcocktailapp.DetailCocktailActivity

@Composable
fun DrinksScreen(modifier: Modifier) {
    val list = listOf( "Manathan", "Pina colada")
    val context = LocalContext.current
    LazyColumn(modifier
        .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(list) { drink ->
            Card(Modifier.clickable {
                val intent = Intent(context, DetailCocktailActivity::class.java)
                context.startActivity(intent)
            }) {
                Text(
                    "${drink}",
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}