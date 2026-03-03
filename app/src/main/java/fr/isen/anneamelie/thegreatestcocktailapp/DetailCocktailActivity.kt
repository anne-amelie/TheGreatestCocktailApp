package fr.isen.anneamelie.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.isen.anneamelie.thegreatestcocktailapp.models.AppBarState
import fr.isen.anneamelie.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.anneamelie.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class DetailCocktailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drinkId = intent.getStringExtra(DRINKID) ?: ""
        enableEdgeToEdge()
        setContent {
            val appBarState = remember { mutableStateOf(AppBarState()) }

            TheGreatestCocktailAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { },
                            navigationIcon = {
                                appBarState.value.onBackClick?.let { onBack ->
                                    Box(
                                        modifier = Modifier
                                            .padding(start = 16.dp)
                                            .size(48.dp)
                                            .clip(CircleShape)
                                            .background(Color.White.copy(alpha = 0.15f))
                                            .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                                            .clickable { onBack() },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back",
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp)
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
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Do not apply innerPadding here to allow content to go behind the bars
                    DetailCocktailScreen(
                        drinkId = drinkId,
                        onComposing = { topBar ->
                            appBarState.value = topBar
                        },
                        contentPadding = innerPadding
                    )
                }
            }
        }
    }

    companion object {
        const val DRINKID = "drinkid"
    }
}