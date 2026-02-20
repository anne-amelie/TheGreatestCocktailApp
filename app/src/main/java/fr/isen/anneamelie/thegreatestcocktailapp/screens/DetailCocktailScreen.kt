package fr.isen.anneamelie.thegreatestcocktailapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.anneamelie.thegreatestcocktailapp.R
import fr.isen.anneamelie.thegreatestcocktailapp.models.Category

@Composable
fun DetailCocktailScreen(modifier: Modifier) {
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
            modifier = modifier.fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Image(
                painterResource(R.drawable.cocktail_bird),
                "",
                contentScale = ContentScale.FillBounds,
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
            Text("Yoghurt Cooler",
                fontSize = 40.sp,
                color = colorResource(R.color.white))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
//                Text("Other / Unknown")
//                Text("Non alcoholic")
                CategoryView(Category.OTHER)
                CategoryView(Category.NON_ALCOHOLIC)
            }
            Text(
                "Cocktail glass",
                color = colorResource(R.color.grey)
            ) // Kind of glass
            Card() {
                Column(
                    Modifier.padding(16.dp)
                        .fillMaxWidth()) {
                    Text(stringResource(R.string.igrendient),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Text("Coca-cola")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                    Text("Lemon juice")
                }
            }
            Card() {
                Column(
                    Modifier.padding(16.dp)
                        .fillMaxWidth()) {
                    Text(stringResource(R.string.preparation),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Text("Take a glass, pour the coke in the glass, then you take 7 drops of lemon juice")
                }
            }
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