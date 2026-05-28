package com.eelizarraras.workout.flows.routine.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.R
import com.eelizarraras.workout.flows.routine.presentation.model.CreateRoutineUIModel
import com.eelizarraras.workout.flows.routine.presentation.model.Workout
import com.eelizarraras.workout.flows.routine.presentation.model.WorkoutSet
import com.eelizarraras.workout.ui.theme.DarkGreyCardBackground
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.flows.routine.presentation.components.InputBox

@Composable
fun CreateRoutineScreen(
    modifier: Modifier = Modifier
) {
    CreateRoutineContent(
        createRoutineUIModel = CreateRoutineUIModel(
            name = "",
            muscle = stringArrayResource(R.array.muscle),
            workouts = mutableListOf()
        ),
        onRoutineNameChange = { name -> },
        onMuscleSelect = { muscle -> },
        addWorkout = {},
        onSave = {}
    )
}

@Preview
@Composable
private fun CreateRoutinePreview() {
    WorkoutTrackerTheme {
        CreateRoutineContent(
            createRoutineUIModel = CreateRoutineUIModel(
                name = "",
                muscle = stringArrayResource(R.array.muscle),
                workouts = mutableListOf(
                    Workout(
                        name = "Pres banca plano",
                        sets = mutableListOf(
                            WorkoutSet(
                                weight = 8.0,
                                workoutUnit = WorkoutUnit.Kg,
                                reps = 5
                            )
                        )
                    )
                )
            ),
            onRoutineNameChange = {},
            onMuscleSelect = { },
            addWorkout = {},
            onSave = { }
        )
    }
}

@Composable
private fun CreateRoutineContent(
    modifier: Modifier = Modifier,
    createRoutineUIModel: CreateRoutineUIModel,
    onRoutineNameChange: (String) -> Unit,
    onMuscleSelect: (String) -> Unit,
    addWorkout: () -> Unit,
    onSave: () -> Unit
) {
    val selectedMuscle by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFF121212),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onSave() },
                shape = CircleShape,
                contentColor = Color(0xFF000080),
                containerColor = Color(0xFFC4D1FF)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Guardar rutina")
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.routine_name_label),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFFC4D1FF),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = createRoutineUIModel.name,
                    onValueChange = { name -> onRoutineNameChange(name) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.routine_name_placeholder),
                            color = Color.White.copy(alpha = 0.3f)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF1E1E1E),
                        unfocusedContainerColor = Color(0xFF1E1E1E),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = TealAccent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                Text(
                    text = stringResource(R.string.muscle_focus_label),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(createRoutineUIModel.muscle) { muscle ->
                        val isSelected = muscle == selectedMuscle
                        FilterChip(
                            selected = isSelected,
                            onClick = { onMuscleSelect(muscle) },
                            label = { Text(muscle) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color.Transparent,
                                labelColor = Color.White,
                                selectedContainerColor = TealAccent,
                                selectedLabelColor = Color.Black
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = Color.White.copy(alpha = 0.3f),
                                selectedBorderColor = Color.Transparent,
                                enabled = true,
                                selected = isSelected
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                }
            }

            items(
                items = createRoutineUIModel.workouts,
                key = { it.name + it.sets }
            ) {
                ExerciseItem(
                    name = it.name,
                    category = "",
                    sets = it.sets
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                OutlinedButton(
                    onClick = { addWorkout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                        width = 1.dp
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.add_exercise),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }
}

@Composable
private fun ExerciseItem(
    name: String,
    category: String,
    sets: MutableList<WorkoutSet>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkGreyCardBackground
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF2C2C3E)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = null,
                        tint = Color(0xFFC4D1FF),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )
                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodySmall,
                        color = TealAccent
                    )
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color(0xFFFF8A8A),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Series
            sets.forEachIndexed { index, set ->
                SetRow(setNumber = index + 1, set = set)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Botón Añadir Serie
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { /* TODO */ },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.add_set),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun SetRow(
    modifier: Modifier = Modifier,
    setNumber: Int,
    set: WorkoutSet
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = setNumber.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(24.dp)
        )
        
        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InputBox(
                modifier = Modifier.weight(2f),
                value = set.weight.toString(),
                placeholder = stringResource(R.string.weight_hint),
                showUnits = true
            )
            InputBox(
                modifier = Modifier.weight(0.8f),
                placeholder = stringResource(R.string.reps_hint),
                value = set.reps.toString()
            )
        }
    }
}