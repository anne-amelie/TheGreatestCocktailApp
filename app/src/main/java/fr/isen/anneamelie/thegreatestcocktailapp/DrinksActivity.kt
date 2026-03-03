package fr.isen.anneamelie.thegreatestcocktailapp

import android.os.Bundle
import android.util.Log
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
import fr.isen.anneamelie.thegreatestcocktailapp.screens.DrinksScreen
import fr.isen.anneamelie.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class DrinksActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("LifeCycle", "DrinksActivity onCreate")

        val categoryID = intent.getStringExtra(CATEGORY) ?: ""

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
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(Modifier.fillMaxSize()) {
                        DrinksScreen(
                            contentPadding = innerPadding,
                            category = categoryID,
                            onComposing = { topBar ->
                                appBarState.value = topBar
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onPause(){
        super.onPause()
        Log.d("LifeCycle", "DrinksActivity onPause")
    }

    override fun onResume(){
        super.onResume()
        Log.d("LifeCycle", "DrinksActivity onResume")
    }

    override fun onStop(){
        super.onStop()
        Log.d("LifeCycle", "DrinksActivity onStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d("LifeCycle", "DrinksActivity onDestroy")
    }

    override fun onStart(){
        super.onStart()
        Log.d("LifeCycle", "DrinksActivity onStart")
    }

    override fun onRestart(){
        super.onRestart()
        Log.d("LifeCycle", "DrinksActivity onRestart")
    }

    companion object {
        const val CATEGORY = "category"
    }
}