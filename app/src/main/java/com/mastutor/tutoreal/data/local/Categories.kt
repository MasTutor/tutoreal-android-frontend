package com.mastutor.tutoreal.data.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Biotech
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Colorize
import androidx.compose.material.icons.outlined.CorporateFare
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val id: String,
    val name: String,
    val icon: ImageVector
)

object CategoriesData {
    val categories = listOf(
        Category(
            id = "",
            name = "All",
            icon = Icons.Outlined.List
        ),

        Category(
            id = "Technology",
            name = "Teknologi",
            icon = Icons.Outlined.CorporateFare
        ),
        Category(
            id = "Arts",
            name = "Seni",
            icon = Icons.Outlined.Colorize
        ),
        Category(
            id = "Multimedia",
            name = "Multimedia",
            icon = Icons.Outlined.Movie
        ),
        Category(
            id = "Music",
            name = "Musik",
            icon = Icons.Outlined.LibraryMusic
        ),
        Category(
            id = "Social",
            name = "Sosial",
            icon = Icons.Outlined.Chat
        ),
        Category(
            id = "Math",
            name = "Matematika",
            icon = Icons.Outlined.Calculate
        ),
        Category(
            id = "Science",
            name = "Sains",
            icon = Icons.Outlined.Biotech
        ),
        Category(
            id = "Language",
            name = "Bahasa",
            icon = Icons.Outlined.Translate
        ),
    )
}
