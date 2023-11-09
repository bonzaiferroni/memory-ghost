package com.bollwerks.memoryghost.ui.study

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bollwerks.memoryghost.R
import com.bollwerks.memoryghost.data.SampleRepository
import com.bollwerks.memoryghost.ui.theme.MemoryGhostTheme
import com.bollwerks.memoryghost.utils.PreviewDark
import com.bollwerks.memoryghost.utils.ezchime.rememberChimePlayer
import com.bollwerks.memoryghost.utils.ezlisten.onResults
import com.bollwerks.memoryghost.utils.ezlisten.rememberSpeechRecognizer
import com.bollwerks.memoryghost.utils.ezlisten.startListening
import com.bollwerks.memoryghost.utils.ezspeak.rememberEzSpeaker
import com.bollwerks.memoryghost.utils.gapLarge
import com.bollwerks.memoryghost.utils.paddingSmall

@Composable
fun StudyScreen(
    viewModel: StudyModel,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val speechRecognizer = rememberSpeechRecognizer(context)
    val speaker = rememberEzSpeaker(context, viewModel::onDoneSpeaking)
    val chime = rememberChimePlayer(R.raw.correct, R.raw.incorrect)

    speechRecognizer.onResults(
        onResults = viewModel::onAnswer,
    )

    LaunchedEffect(uiState.isListening) {
        if (uiState.isListening) {
            speechRecognizer.startListening(
                biasingStrings = uiState.answers,
            )
        }
    }

    val question = uiState.question
    LaunchedEffect(question) {
        if (uiState.speak && question != null) {
            speaker.speak(question)
        }
    }

    LaunchedEffect(uiState.isCorrect) {
        uiState.isCorrect?.let {
            if (it) {
                chime.play(R.raw.correct)
            } else {
                chime.play(R.raw.incorrect)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .paddingSmall(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                onClick = viewModel::startQuiz,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Study")
            }
            Text(uiState.message)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = uiState.listen,
                onCheckedChange = {
                    viewModel.toggleListen()
                },
                thumbContent = {
                    Text("🎤")
                }
            )
            Switch(
                checked = uiState.speak,
                onCheckedChange = {
                    viewModel.toggleSpeak()
                },
                thumbContent = {
                    Text("🗣")
                }
            )
        }
        Spacer(modifier = Modifier.gapLarge())
        uiState.question?.let { question ->
            Text(
                text = question,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            uiState.answers.forEach { answer ->
                Button(onClick = {
                    viewModel.onAnswer(answer)
                }) {
                    Text(answer)
                }
            }
        }
    }
}

@PreviewDark
@Composable
fun StudyScreenPreview() {
    MemoryGhostTheme {
        StudyScreen(
            viewModel = StudyModel(dataRepository = SampleRepository()),
        )
    }
}
