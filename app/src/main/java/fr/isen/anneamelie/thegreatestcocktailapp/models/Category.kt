package fr.isen.anneamelie.thegreatestcocktailapp.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import fr.isen.anneamelie.thegreatestcocktailapp.R

enum class Category {
    BEER,
    COCKTAIL,
    COCOA,
    COFFE,
    LIQUOR,
    DRINK,
    PUNCH,
    SHAKE,
    SHOT,
    SOFT,
    ALCOHOLIC,
    NON_ALCOHOLIC,
    OTHER;

    companion object {
        fun allObjects(): List<Category> {
            return listOf(
                BEER,
                COCKTAIL,
                COCOA,
                COFFE,
                LIQUOR,
                DRINK,
                PUNCH,
                SHAKE,
                SHOT,
                SOFT,
                OTHER
            )
        }
        fun toString(category: Category): String {
            return when(category) {
                ALCOHOLIC -> "Alcoholic"
                NON_ALCOHOLIC -> "Non alcoholic"
                OTHER -> "Other / Unknown"
                BEER -> "Beer"
                COCKTAIL -> "Cocktail"
                COCOA -> "Cocoa"
                COFFE -> "Coffe"
                LIQUOR -> "Homemade Liquor"
                DRINK -> "Ordinary Drink"
                PUNCH -> "Punch / Party Drink"
                SHAKE -> "Shake"
                SHOT -> "Shot"
                SOFT -> "Soft Drink"
            }
        }

        @Composable
        fun colors(category: Category): List<Color> {
            return when(category) {
                ALCOHOLIC -> listOf(
                    colorResource(R.color.orange_200),
                    colorResource(R.color.orange_700)
                )

                NON_ALCOHOLIC -> listOf(
                    colorResource(R.color.orange_200),
                    colorResource(R.color.orange_700)
                )

                OTHER -> listOf(
                    colorResource(R.color.teal_200),
                    colorResource(R.color.teal_700)
                )
                BEER -> listOf(
                    colorResource(R.color.teal_200),
                    colorResource(R.color.teal_700)
                )
                COCKTAIL -> listOf(
                    colorResource(R.color.teal_200),
                    colorResource(R.color.teal_700)
                )
                COCOA -> listOf(
                    colorResource(R.color.teal_200),
                    colorResource(R.color.teal_700)
                )
                COFFE -> listOf(
                    colorResource(R.color.teal_200),
                    colorResource(R.color.teal_700)
                )
                LIQUOR -> listOf(
                    colorResource(R.color.teal_200),
                    colorResource(R.color.teal_700)
                )
                DRINK -> listOf(
                    colorResource(R.color.teal_200),
                    colorResource(R.color.teal_700)
                )
                PUNCH -> listOf(
                    colorResource(R.color.teal_200),
                    colorResource(R.color.teal_700)
                )
                SHAKE -> listOf(
                    colorResource(R.color.teal_200),
                    colorResource(R.color.teal_700)
                )
                SHOT -> listOf(
                    colorResource(R.color.teal_200),
                    colorResource(R.color.teal_700)
                )
                SOFT -> listOf(
                    colorResource(R.color.teal_200),
                    colorResource(R.color.teal_700)
                )
            }
        }
    }
}