package com.example.a501_a5.data

data class Category(
    val name: String,
    val description: String,
)

data class Location(
    val id: Int,
    val name: String,
    val category: String,
    val shortDescription: String,
    val address: String,
    val highlights: List<String>,
)

private val categories = listOf(
    Category(
        name = "Museums",
        description = "Innovative exhibits and local history.",
    ),
    Category(
        name = "Parks",
        description = "Green escapes woven through the city.",
    ),
    Category(
        name = "Restaurants",
        description = "Local flavors and iconic bites.",
    ),
    Category(
        name = "Landmarks",
        description = "Signature city sights and skyline views.",
    ),
)

private val locations = listOf(
    Location(
        id = 101,
        name = "Downtown Innovation Museum",
        category = "Museums",
        shortDescription = "Interactive exhibits on emerging tech.",
        address = "12 Tech Way",
        highlights = listOf(
            "Holographic city timeline",
            "Robotics lab demonstrations",
            "Nighttime projection show",
        ),
    ),
    Location(
        id = 102,
        name = "City Heritage Gallery",
        category = "Museums",
        shortDescription = "Artifacts from the city's harbor origins.",
        address = "221 Harbor St.",
        highlights = listOf(
            "Immersive harbor rebuild",
            "Rotating artist residency",
        ),
    ),
    Location(
        id = 201,
        name = "Harborfront Park",
        category = "Parks",
        shortDescription = "Sunset boardwalk and kayak launches.",
        address = "Pier 5",
        highlights = listOf(
            "Floating gardens",
            "Weekend waterfront market",
            "Kayak rentals",
        ),
    ),
    Location(
        id = 202,
        name = "Skyline Overlook",
        category = "Parks",
        shortDescription = "Hilltop trails with skyline views.",
        address = "99 Summit Ave.",
        highlights = listOf(
            "Sunrise yoga deck",
            "Glass canopy lookout",
        ),
    ),
    Location(
        id = 301,
        name = "Mercury Oyster Bar",
        category = "Restaurants",
        shortDescription = "Seafood menu with rooftop seating.",
        address = "54 Wharf Rd.",
        highlights = listOf(
            "Harbor-to-table raw bar",
            "Rooftop jazz nights",
        ),
    ),
    Location(
        id = 302,
        name = "Union Square Market Hall",
        category = "Restaurants",
        shortDescription = "Local vendors under one glass roof.",
        address = "41 Market Row",
        highlights = listOf(
            "Seasonal tasting flights",
            "Live demo kitchen",
        ),
    ),
    Location(
        id = 401,
        name = "Beacon Spire",
        category = "Landmarks",
        shortDescription = "Observation deck atop the historic tower.",
        address = "1 Beacon Plaza",
        highlights = listOf(
            "360° skyline walkway",
            "Historic signal room",
        ),
    ),
    Location(
        id = 402,
        name = "Old Foundry Yard",
        category = "Landmarks",
        shortDescription = "Industrial ruins turned arts hub.",
        address = "7 Ironworks Ln.",
        highlights = listOf(
            "Weekend artisan market",
            "Nighttime light installations",
        ),
    ),
)

fun getCategories(): List<Category> = categories

fun getLocationsForCategory(category: String): List<Location> =
    locations.filter { it.category.equals(category, ignoreCase = true) }

fun findLocation(category: String, locationId: Int): Location? =
    locations.firstOrNull {
        it.category.equals(category, ignoreCase = true) && it.id == locationId
    }
