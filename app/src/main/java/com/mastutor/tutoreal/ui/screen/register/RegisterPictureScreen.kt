package com.mastutor.tutoreal.ui.screen.register

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mastutor.tutoreal.R
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.util.AuthUiState
import com.mastutor.tutoreal.viewmodel.AuthViewModel

@Composable
fun RegisterPictureScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: AuthViewModel
) {
    val context = LocalContext.current
    val imageUri by viewModel.imageUri
    val name by viewModel.fullNameRegister
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.changeUri(uri)
        }
    }

    viewModel.registerResponse.collectAsState(initial = AuthUiState.Idle).value.let { uiState ->
        when (uiState) {
            is AuthUiState.Idle -> {
                RegisterPictureContent(
                    modifier = modifier,
                    name = name,
                    imageUri = imageUri,
                    onEditClicked = {
                        launcher.launch("image/*")
                    },
                    onRegisterClicked = {
                        viewModel.register()
                    },
                    onBackClicked = {
                        navHostController.popBackStack()
                    }
                )
            }

            is AuthUiState.Load -> {
                RegisterPictureContent(
                    modifier = modifier.alpha(0.3f),
                    name = name,
                    imageUri = imageUri,
                    onEditClicked = {},
                    onRegisterClicked = {},
                    onBackClicked = {}
                )
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Loading")
                    CircularProgressIndicator(color = Color.Black)
                }
            }

            is AuthUiState.Success -> {
                LaunchedEffect(key1 = true) {
                    Toast.makeText(context, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    navHostController.navigate(Screen.Chooser.route) {
                        popUpTo(0)
                    }
                }
            }

            is AuthUiState.Failure -> {
                RegisterPictureContent(
                    modifier = modifier,
                    name = name,
                    imageUri = imageUri,
                    onEditClicked = {
                        launcher.launch("image/*")
                    },
                    onRegisterClicked = {
                        viewModel.register()
                    },
                    onBackClicked = {
                        navHostController.popBackStack()
                    }
                )
                LaunchedEffect(key1 = true) {
                    Toast.makeText(
                        context,
                        "Gagal cek input, atau cek koneksi internet",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }
}

@Composable
fun RegisterPictureContent(
    modifier: Modifier,
    name: String,
    imageUri: Uri,
    onEditClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, top = 5.dp)
                .offset(y = 15.dp)
                .clickable { onBackClicked() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Arrow Forward",
                modifier = Modifier.padding(end = 8.dp),
                tint = Color.Black
            )
            Text(
                text = "Kembali",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black),
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1F)
        )

        Text(
            "Choose your profile picture",
            modifier = modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier
                .height(50.dp)
        )

        Box(
            modifier = modifier
                .size(354.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val initialBitmap = BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.profile_placeholder
                )

                Image(
                    bitmap =
                    if (imageUri != Uri.EMPTY) {
                        val inputStream =
                            imageUri.let { context.contentResolver.openInputStream(it) }
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        bitmap.asImageBitmap()
                    } else {
                        initialBitmap.asImageBitmap()
                    },
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(218.dp)
                        .clip(CircleShape)
                        .clickable { onEditClicked() }
                )

                Spacer(modifier = modifier.height(32.dp))

                Text(
                    name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1F)
        )

        Button(
            onClick = onRegisterClicked,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text("Register")
        }
    }
}