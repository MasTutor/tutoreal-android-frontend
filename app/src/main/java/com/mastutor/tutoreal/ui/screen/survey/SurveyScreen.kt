package com.mastutor.tutoreal.ui.screen.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastutor.tutoreal.data.local.QuestionData
import com.mastutor.tutoreal.ui.theme.TutorealTheme

//Stateful
@Composable
fun SurveyScreen(){

}

//Stateless and please don't make this shit stateful
@Composable
fun SurveyContent(
    question: QuestionData,
    choices: List<String>,
    selectedChoice: String,
    onChoiceSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    onNextClicked: () -> Unit,
){
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 20.dp)
    ) {
        Text(
            text = question.question,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        AsyncImage(
            model = question.questionImg,
            contentDescription = question.question,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .clip(RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .size(160.dp)

        )
        choices.forEach {choice ->
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .height(44.dp)

                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )

                .background(
                    color = if (choice == selectedChoice) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .selectable(selected = (choice == selectedChoice), onClick = {onChoiceSelected(choice)})

            ){
                Text(
                    text = choice,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                    color = if (choice == selectedChoice) Color.White else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
        Spacer(modifier = Modifier
            .size(8.dp)
            .weight(1F))
        Button(onClick = onNextClicked, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp), shape = RoundedCornerShape(8.dp)) {
            Text("Next")
        }
    }
}

@Preview(device = "id:pixel_5", showSystemUi = true, backgroundColor = 0xFFE8F0F9, showBackground = true)
@Composable
fun SurveyContentPreview(){
    TutorealTheme {
        val choices = listOf("Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree")
        var (selectedChoice, onChoiceSelected) = remember {
            mutableStateOf(choices[0])
        }
        SurveyContent(question = QuestionData(question = "Apa kamu furry?", questionImg = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg"),
            choices = choices,
            selectedChoice = selectedChoice,
            onChoiceSelected = onChoiceSelected,
            onNextClicked = {}
        )
    }
}