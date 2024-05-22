package net.iessochoa.mireyamartinez.filmop.data

data class MovieData(
    val movieId: String = "",
    val name: String = "",
    val duration: String = "",
    val genre: String = "",
    val rating: Double = 0.0,
    val platforms: List<String> = emptyList()
)
