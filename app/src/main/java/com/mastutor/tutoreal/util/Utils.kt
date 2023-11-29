package com.mastutor.tutoreal.util

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
    return email.matches(emailRegex)
}

private fun createTempFile(context: Context): File {
    val fileName = "temp_file"
    val directory = context.cacheDir
    return File(directory, fileName)
}
suspend fun uriToFile(uri: Uri, context: Context): File = withContext(Dispatchers.IO) {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = createTempFile(context)
    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return@withContext file
}