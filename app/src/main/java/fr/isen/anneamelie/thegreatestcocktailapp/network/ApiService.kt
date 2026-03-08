package fr.isen.anneamelie.thegreatestcocktailapp.network

import fr.isen.anneamelie.thegreatestcocktailapp.dataClasses.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    fun getRandomCocktail(): Call<CocktailResponse>

    @GET("random.php")
    suspend fun getRandom(): CocktailResponse

    @GET("list.php?c=list")
    fun getCategories(): Call<CategoryListResponse>

    @GET("filter.php")
    fun getDrinksPreview(@Query("c") categoryID: String): Call<DrinkFilterResponse>

    @GET("lookup.php")
    fun getDetailCocktail(@Query("i") drinkID: String): Call<CocktailResponse>

    @GET("search.php")
    fun searchCocktails(@Query("s") query: String): Call<CocktailResponse>

    @GET("filter.php")
    fun searchByIngredient(@Query("i") ingredient: String): Call<DrinkFilterResponse>
}
