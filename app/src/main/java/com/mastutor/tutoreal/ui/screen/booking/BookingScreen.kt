package com.mastutor.tutoreal.ui.screen.booking

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.TextFields
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mastutor.tutoreal.ui.components.AlertDialog
import com.mastutor.tutoreal.ui.components.TutorComponent
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.util.AuthUiState
import com.mastutor.tutoreal.util.separateDate
import com.mastutor.tutoreal.viewmodel.TutorViewModel
import java.util.Calendar
import java.util.Locale

@SuppressLint("NewApi")
@Composable
fun BookingScreen(
    modifier: Modifier = Modifier,
    viewModel: TutorViewModel,
    navHostController: NavHostController
) {
    SideEffect {
        viewModel.getToken()
    }

    val context = LocalContext.current
    val tutor by viewModel.tutorData
    val date by viewModel.sessionDate
    val title by viewModel.sessionTitle

    val titleError by viewModel.titleError
    val dateError by viewModel.dateError

    val successDialog = remember {
        mutableStateOf(false)
    }

    var day by remember {
        mutableStateOf("")
    }
    val datePicker = DatePickerDialog(context)

    val cal = Calendar.getInstance()
    datePicker.datePicker.minDate = cal.timeInMillis
    datePicker.setOnDateSetListener { _, year, month, dayOfMonth ->
        viewModel.changeDate("$year-$month-$dayOfMonth")
        viewModel.changeDateError(false)
    }
    day = (if (date.isNotEmpty()) {
        val (y, m, d) = separateDate(date)
        val localeIndonesian = Locale("id", "ID")
        cal.set(Calendar.YEAR, y)
        cal.set(Calendar.MONTH, m)
        cal.set(Calendar.DAY_OF_MONTH, d)
        cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, localeIndonesian)?.toString() + ", "
    } else {
        ""
    }).toString()

    AlertDialog(text = "OK!", openDialog = successDialog, onSubmitClicked = {
        navHostController.navigate(Screen.Schedule.route) {
            popUpTo(Screen.Book.route) {
                inclusive = true
            }
        }
    })

    viewModel.bookResponse.collectAsState(initial = AuthUiState.Idle).value.let { uiState ->
        when (uiState) {
            is AuthUiState.Idle -> {
                BookingContent(
                    modifier = modifier,
                    onBackClicked = { navHostController.navigateUp() },
                    sessionTitle = title,
                    onSessionTitleChanged = {
                        viewModel.changeTitle(it)
                        viewModel.changeTitleError(title.isEmpty())
                    },
                    onBookClicked = {
                        viewModel.changeTitleError(title.isEmpty())
                        viewModel.changeDateError(date.isEmpty())
                        if (!titleError && !dateError) {
                            viewModel.bookTutor(
                                id = tutor.id,
                                title = title,
                                date = date,
                                status = "OnGoing"
                            )
                        }
                    },
                    onDateClicked = {
                        datePicker.show()
                        viewModel.changeDateError(date.isEmpty())
                    },
                    date = "$day$date",
                    price = tutor.price.ifEmpty { "Rp 30.000" },
                    name = tutor.nama,
                    picture = tutor.picture.ifEmpty { "https://data.1freewallpapers.com/detail/face-surprise-emotions-vector-art-minimalism.jpg" },
                    job = tutor.specialization,
                    sessionTitleError = titleError,
                    dateError = dateError
                )
            }

            is AuthUiState.Load -> {
                BookingContent(
                    onBackClicked = { },
                    sessionTitle = title,
                    onSessionTitleChanged = { },
                    onBookClicked = { },
                    onDateClicked = { },
                    date = "$day$date",
                    price = tutor.price.ifEmpty { "Rp 30.000" },
                    name = tutor.nama,
                    picture = tutor.picture.ifEmpty { "https://data.1freewallpapers.com/detail/face-surprise-emotions-vector-art-minimalism.jpg" },
                    job = tutor.specialization,
                    sessionTitleError = titleError,
                    dateError = dateError,
                    modifier = modifier.alpha(0.3f)
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
                BookingContent(
                    onBackClicked = { },
                    sessionTitle = title,
                    onSessionTitleChanged = { },
                    onBookClicked = { },
                    onDateClicked = { },
                    date = "$day$date",
                    price = tutor.price.ifEmpty { "Rp 30.000" },
                    name = tutor.nama,
                    picture = tutor.picture.ifEmpty { "https://data.1freewallpapers.com/detail/face-surprise-emotions-vector-art-minimalism.jpg" },
                    job = tutor.specialization,
                    modifier = modifier,
                    sessionTitleError = titleError,
                    dateError = dateError
                )
                LaunchedEffect(key1 = true) {
                    successDialog.value = true
                }
            }

            is AuthUiState.Failure -> {
                BookingContent(modifier = modifier,
                    onBackClicked = { navHostController.navigateUp() },
                    sessionTitle = title,
                    onSessionTitleChanged = viewModel::changeTitle,
                    onBookClicked = {
                        viewModel.bookTutor(
                            id = tutor.id,
                            title = title,
                            date = date,
                            status = "Ongoing"
                        ) // token
                    }, onDateClicked = { datePicker.show() }, date = "$day$date",
                    price = tutor.price.ifEmpty { "Rp 30.000" },
                    name = tutor.nama,
                    picture = tutor.picture.ifEmpty { "https://data.1freewallpapers.com/detail/face-surprise-emotions-vector-art-minimalism.jpg" },
                    job = tutor.specialization,
                    sessionTitleError = titleError,
                    dateError = dateError
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingContent(
    modifier: Modifier = Modifier,
    sessionTitle: String,
    sessionTitleError: Boolean,
    name: String,
    picture: String,
    job: String,
    date: String,
    dateError: Boolean,
    price: String,
    onSessionTitleChanged: (String) -> Unit,
    onBackClicked: () -> Unit,
    onBookClicked: () -> Unit,
    onDateClicked: () -> Unit
) {
    Box(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 10.dp, bottom = 40.dp)
                    .clickable { onBackClicked() }
                    ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Arrow Forward",
                    modifier = Modifier.padding(end = 8.dp, ),
                    tint = Color.Black
                )
            }
            Text(
                "Judul Sesi Anda",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            TextField(
                shape = RoundedCornerShape(8.dp),
                value = sessionTitle,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    disabledLabelColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                onValueChange = onSessionTitleChanged,
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Masukkan Judul!",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = if (sessionTitleError) MaterialTheme.colorScheme.error else Color.Gray
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.TextFields, contentDescription = "Name", tint = Color.Black)
                },
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )
            Text(
                "Tutor Anda",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            TutorComponent(
                photoUrl = picture,
                name = name,
                job = job,
                price = price,
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
            )
            Text(
                "Pilih Tanggal Anda",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onDateClicked)
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(start = 5.dp)
                    .height(64.dp)

            ) {
                Row(modifier = Modifier.align(Alignment.CenterStart)){
                    Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "date", tint = Color.Black, modifier = Modifier.padding(end = 20.dp, start = 10.dp))
                    Text(
                        date.ifEmpty { "Tekan untuk memilih tanggal!" },
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = if (dateError) MaterialTheme.colorScheme.error else Color.Black
                        ),
                    )
                }

            }

            Spacer(modifier = Modifier.weight(1F))
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        ) {
            Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 40.dp)) {
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .width(30.dp)
                        .padding(top = 10.dp, bottom = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
                ) {
                    Text(
                        "Total Pembayaran:", style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal
                        ), color = Color.Gray
                    )
                    Spacer(Modifier.weight(1F))
                    Text(price, color = Color.Black)
                }


                Button(
                    onClick = onBookClicked,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    enabled = (!sessionTitleError && !dateError)
                ) {
                    Text("Jadwalkan Sesi")
                }
            }
        }
    }
}