package com.example.a501_a5

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import java.util.UUID

data class Recipe(
    val id: String,
    val title: String,
    val ingredients: List<String>,
    val steps: List<String>
)

class RecipeViewModel : ViewModel() {
    private val _recipes = mutableStateListOf(
        Recipe(
            id = UUID.randomUUID().toString(),
            title = "Lemon Herb Chicken",
            ingredients = listOf(
                "2 chicken breasts",
                "1 lemon, juiced",
                "2 cloves garlic",
                "Fresh thyme",
                "Salt & pepper"
            ),
            steps = listOf(
                "Marinate chicken with lemon juice, garlic, thyme, salt, and pepper.",
                "Heat skillet over medium heat with olive oil.",
                "Sear chicken on both sides until cooked through.",
                "Let rest for 5 minutes before slicing."
            )
        ),
        Recipe(
            id = UUID.randomUUID().toString(),
            title = "Vegetable Stir Fry",
            ingredients = listOf(
                "2 cups mixed vegetables",
                "1 tbsp soy sauce",
                "1 tsp sesame oil",
                "1 clove garlic",
                "1 tsp grated ginger"
            ),
            steps = listOf(
                "Heat wok or large pan over high heat.",
                "Add sesame oil, garlic, and ginger; stir until fragrant.",
                "Add vegetables and stir fry for 4-5 minutes.",
                "Add soy sauce and toss to coat evenly."
            )
        )
    )

    val recipes: SnapshotStateList<Recipe> = _recipes

    fun findRecipeById(id: String?): Recipe? =
        id?.let { lookup -> _recipes.firstOrNull { it.id == lookup } }

    fun addRecipe(
        rawTitle: String,
        rawIngredients: String,
        rawSteps: String
    ): String? {
        val title = rawTitle.trim()
        if (title.isEmpty()) {
            return null
        }

        val ingredients = rawIngredients
            .lines()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .ifEmpty { listOf("No ingredients provided.") }

        val steps = rawSteps
            .lines()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .ifEmpty { listOf("No steps added yet.") }

        val recipe = Recipe(
            id = UUID.randomUUID().toString(),
            title = title,
            ingredients = ingredients,
            steps = steps
        )
        _recipes.add(0, recipe)
        return recipe.id
    }
}
