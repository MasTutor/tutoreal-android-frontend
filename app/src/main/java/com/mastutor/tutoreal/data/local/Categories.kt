package com.mastutor.tutoreal.data.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Translate
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
            icon = Icons.Filled.List
        ),

        Category(
            id = "Technology",
            name = "Teknologi",
            icon = Icons.Filled.Computer
        ),
        Category(
            id = "Arts",
            name = "Seni",
            icon = Icons.Filled.Brush
        ),
        Category(
            id = "Multimedia",
            name = "Multimedia",
            icon = Icons.Filled.Movie
        ),
        Category(
            id = "Music",
            name = "Musik",
            icon = Icons.Filled.MusicNote
        ),
        Category(
            id = "Social",
            name = "Sosial",
            icon = Icons.Filled.ChatBubble
        ),
        Category(
            id = "Math",
            name = "Matematika",
            icon = Icons.Filled.Calculate
        ),
        Category(
            id = "Science",
            name = "Sains",
            icon = Icons.Filled.Biotech
        ),
        Category(
            id = "Language",
            name = "Bahasa",
            icon = Icons.Filled.Translate
        ),
    )
}
