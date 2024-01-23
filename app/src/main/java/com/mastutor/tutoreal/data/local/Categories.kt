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
    val icon: ImageVector,
    val idx: Int
)

object CategoriesData {
    val categories = listOf(
        Category(
            id = "",
            name = "All",
            icon = Icons.Outlined.List,
            idx = 0
        ),

        Category(
            id = "Technology",
            name = "Teknologi",
            icon = Icons.Outlined.CorporateFare,
            idx = 1
        ),
        Category(
            id = "Arts",
            name = "Seni",
            icon = Icons.Outlined.Colorize,
            idx = 2
        ),
        Category(
            id = "Multimedia",
            name = "Multimedia",
            icon = Icons.Outlined.Movie,
            idx = 3
        ),
        Category(
            id = "Music",
            name = "Musik",
            icon = Icons.Outlined.LibraryMusic,
            idx = 4
        ),
        Category(
            id = "Social",
            name = "Sosial",
            icon = Icons.Outlined.Chat,
            idx = 5
        ),
        Category(
            id = "Math",
            name = "Matematika",
            icon = Icons.Outlined.Calculate,
            idx = 6
        ),
        Category(
            id = "Science",
            name = "Sains",
            icon = Icons.Outlined.Biotech,
            idx = 7
        ),
        Category(
            id = "Language",
            name = "Bahasa",
            icon = Icons.Outlined.Translate,
            idx = 8
        ),
    )
    val categoriesNoAll = listOf(
        Category(
            id = "Technology",
            name = "Teknologi",
            icon = Icons.Outlined.CorporateFare,
            idx = 1
        ),
        Category(
            id = "Arts",
            name = "Seni",
            icon = Icons.Outlined.Colorize,
            idx = 2
        ),
        Category(
            id = "Multimedia",
            name = "Multimedia",
            icon = Icons.Outlined.Movie,
            idx = 3
        ),
        Category(
            id = "Music",
            name = "Musik",
            icon = Icons.Outlined.LibraryMusic,
            idx = 4
        ),
        Category(
            id = "Social",
            name = "Sosial",
            icon = Icons.Outlined.Chat,
            idx = 5
        ),
        Category(
            id = "Math",
            name = "Matematika",
            icon = Icons.Outlined.Calculate,
            idx = 6
        ),
        Category(
            id = "Science",
            name = "Sains",
            icon = Icons.Outlined.Biotech,
            idx = 7
        ),
        Category(
            id = "Language",
            name = "Bahasa",
            icon = Icons.Outlined.Translate,
            idx = 8
        ),
    )
}
