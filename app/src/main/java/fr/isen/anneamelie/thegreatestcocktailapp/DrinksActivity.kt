package fr.isen.anneamelie.thegreatestcocktailapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fr.isen.anneamelie.thegreatestcocktailapp.screens.DrinksScreen
import fr.isen.anneamelie.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class DrinksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("LifeCycle", "DrinksActivity onCreate")
        setContent {
            val context = LocalContext.current
            TheGreatestCocktailAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DrinksScreen(Modifier.padding(innerPadding))
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
}