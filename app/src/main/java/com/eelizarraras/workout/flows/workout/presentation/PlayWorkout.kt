package com.eelizarraras.workout.flows.workout.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.R
import com.eelizarraras.workout.ui.theme.DarkGreyCardBackground
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Composable
fun PlayWorkoutScreen() {
    // Nivel 1: Stateful
    var isPaused by remember { mutableStateOf(false) }
    val timerValue = "00:24:15" // Esto vendría de un ViewModel/Timer

    Content(
        timerValue = timerValue,
        isPaused = isPaused,
        onPauseToggle = { isPaused = !isPaused },
        onFinishClick = {}
    )
}

@Composable
private fun Content(
    timerValue: String,
    isPaused: Boolean,
    onPauseToggle: () -> Unit,
    onFinishClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Nivel 2: Stateless
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFF121212)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Timer Section
            Text(
                text = timerValue,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 64.sp,
                    color = Color(0xFFC4D1FF)
                )
            )
            Text(
                text = stringResource(R.string.current_duration),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.6f),
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onPauseToggle,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    border = ButtonDefaults.outlinedButtonBorder(enabled = true),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text(
                        text = if (isPaused) "Reanudar" else stringResource(R.string.pause),
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onFinishClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B0000), // Rojo oscuro
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.finish),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Exercise List
            ActiveExerciseCard(
                name = "Barbell Squats",
                setsInfo = "4 Sets • 8-10 Reps",
                isExpanded = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            ActiveExerciseCard(
                name = "Dumbbell Bench Press",
                setsInfo = "3 Sets • 12 Reps",
                isExpanded = false
            )
        }
    }
}

@Composable
private fun ActiveExerciseCard(
    name: String,
    setsInfo: String,
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreyCardBackground)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFC4D1FF),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color(0xFFC4D1FF)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.6f)
                )
            }
            Text(
                text = setsInfo,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.6f)
            )

            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Header Table
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        stringResource(R.string.set),
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                    Text(
                        "kg",
                        textAlign = TextAlign.Center,
                        color = TealAccent,
                        fontSize = 12.sp
                    )
                    Text(
                        stringResource(R.string.reps),
                        textAlign = TextAlign.Center,
                        color = TealAccent,
                        fontSize = 12.sp
                    )
                    Icon(
                        Icons.Default.DoneAll,
                        null,
                        tint = Color.White.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ActiveSetRow(setNumber = 1, weight = "80", reps = "10", isChecked = true)
                Spacer(modifier = Modifier.height(8.dp))
                ActiveSetRow(setNumber = 2, weight = "80", reps = "10", isChecked = false)
            }
        }
    }
}

@Composable
private fun ActiveSetRow(
    setNumber: Int,
    weight: String,
    reps: String,
    isChecked: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isChecked) Color.White.copy(alpha = 0.05f) else Color.Transparent)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = setNumber.toString(),
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = weight, color = Color.White.copy(alpha = 0.4f))
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = reps, color = Color.White.copy(alpha = 0.4f))
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isChecked) TealAccent else Color.Transparent)
                .border(1.dp, if (isChecked) Color.Transparent else Color.White.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                .clickable { /* Toggle */ }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun PlayWorkoutPreview() {
    WorkoutTrackerTheme {
        PlayWorkoutScreen()
    }
}
