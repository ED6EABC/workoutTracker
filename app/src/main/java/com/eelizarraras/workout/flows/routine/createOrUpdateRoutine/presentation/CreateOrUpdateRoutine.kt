package com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eelizarraras.workout.R
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.CreateRoutineState
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.Workout
import com.eelizarraras.workout.ui.theme.DarkGreyCardBackground
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.core.presentation.components.WarningCard
import com.eelizarraras.workout.core.presentation.model.WarningCardModel
import com.eelizarraras.workout.core.presentation.model.WorkoutSet
import com.eelizarraras.workout.core.presentation.views.componets.LoadingView
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.RoutineEffect
import com.eelizarraras.workout.flows.routine.components.InputBox
import com.eelizarraras.workout.flows.routine.components.SuccessAnimation
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.model.RoutineEvent
import com.eelizarraras.workout.flows.routine.createOrUpdateRoutine.presentation.viewModel.RoutineManagerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateOrUpdateRoutineScreen(
    viewModel: RoutineManagerViewModel = koinViewModel(),
    routineId: Long?,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showLoading by remember { mutableStateOf(false) }
    var showWarningCard by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onEvent(RoutineEvent.ResetToInitialState)
    }

    LoadingView(showLoading) {
        CreateRoutineContent(
            state = state,
            onIntent = viewModel::onEvent
        )

        SuccessAnimation(
            startAnimation = state.showAnimation,
            onCompleted = {
                onNavigateBack()
                viewModel.onEvent(RoutineEvent.ResetToInitialState)
            }
        )
    }

    LaunchedEffect(routineId) {
        viewModel.onEvent(RoutineEvent.LoadRoutineToUpdate(routineId))
    }

    BackHandler((state.name.isNotEmpty() || state.workouts.isNotEmpty()) && !state.isUpdating) {
        viewModel.onEvent(RoutineEvent.ShowConfirmation(true))
    }

    if(showWarningCard) {

        val titleLabel: Int
        val supportLabel: Int

        if(state.isUpdating) {
            titleLabel = R.string.update_routine_title
            supportLabel = R.string.update_routine_description
        } else {
            if(state.isNavigationBack) {
                titleLabel = R.string.create_routine_title
                supportLabel = R.string.abort_create_routine_description
            } else {
                titleLabel = R.string.create_routine_title
                supportLabel = R.string.create_routine_description
            }
        }

        WarningCard(
            model = WarningCardModel(
                tittle = stringResource(titleLabel),
                support = stringResource(supportLabel),
                confirmButtonLabel = stringResource(R.string.accept_label),
                dismissButtonLabel = stringResource(R.string.cancel_label),
                onConfirm = {
                    if(state.isNavigationBack) { // Navigate back
                        onNavigateBack()
                    } else { // Save
                        viewModel.onEvent(RoutineEvent.Save(state))
                    }

                    showWarningCard = false
                },
                onDismiss = {
                    showWarningCard = false
                }
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is RoutineEffect.ShowLoading -> {
                    showLoading = effect.isLoading
                }
                RoutineEffect.ShowConfirmationDialog -> {
                    showWarningCard = true
                }
            }
        }
    }
}

@Preview
@Composable
private fun CreateRoutinePreview() {
    WorkoutTrackerTheme {
        CreateRoutineContent(
            state = CreateRoutineState(
                routineId = 0,
                name = "",
                workouts = listOf(
                    Workout(
                        name = "Pres banca plano",
                        sets = listOf(
                            WorkoutSet(
                                weight = "8.0",
                                workoutUnit = WorkoutUnit.Kg,
                                reps = "5"
                            )
                        )
                    ),
                    Workout(
                        name = "Pres banca plano",
                        sets = listOf(
                            WorkoutSet(
                                weight = "9.0",
                                workoutUnit = WorkoutUnit.Kg,
                                reps = "6"
                            ),
                            WorkoutSet(
                                weight = "9.0",
                                workoutUnit = WorkoutUnit.Kg,
                                reps = "6"
                            )
                        )
                    )
                ),
                showAnimation = true
            ),
            onIntent = {}
        )
    }
}

@Composable
private fun CreateRoutineContent(
    state: CreateRoutineState,
    onIntent: (RoutineEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF121212),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if(state.isSaveButtonEnabled) {
                        onIntent(RoutineEvent.ShowConfirmation(false))
                    }
                },
                shape = CircleShape,
                contentColor = Color(0xFF000080),
                containerColor = Color(0xFFC4D1FF),
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
                    value = state.name,
                    onValueChange = { name -> onIntent(RoutineEvent.SetName(name)) },
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
                        unfocusedTextColor = Color.White,
                        errorTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    isError = state.isNameError,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            items(
                items = state.workouts,
                key = { it.uid }
            ) {
                ExerciseItem(
                    category = "",
                    workout = it,
                    onIntent = onIntent
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                OutlinedButton(
                    onClick = { onIntent(RoutineEvent.AddWorkout) },
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
    modifier: Modifier = Modifier,
    category: String,
    workout: Workout,
    onIntent: (RoutineEvent) -> Unit,
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

                    TextField(
                        value = workout.name,
                        onValueChange = { name ->
                            onIntent(RoutineEvent.SetWorkoutName(workout.uid, name))
                        },
                        textStyle = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        placeholder = {
                            Text(text = stringResource(R.string.example_name))
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {}
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF1E1E1E),
                            unfocusedContainerColor = Color(0xFF1E1E1E),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            errorTextColor = Color.White,
                            errorContainerColor = Color.Transparent
                        ),
                        isError = workout.isNameError,
                        singleLine = true
                    )
                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodySmall,
                        color = TealAccent
                    )
                }
                IconButton(onClick = { onIntent(RoutineEvent.DeleteWorkout(workout.uid)) } ) {
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
            workout.sets.forEachIndexed { index, set ->
                SetRow(
                    setNumber = index + 1,
                    workoutId = workout.uid,
                    set = set,
                    onIntent = onIntent
                )
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
                    .clickable { onIntent(RoutineEvent.AddSet(workout.uid)) },
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

@Preview
@Composable
private fun SetRowPreview() {
    SetRow(
        setNumber = 1,
        workoutId = "sdfsdf",
        set = WorkoutSet(
            weight = "6",
            workoutUnit = WorkoutUnit.Lbs,
            reps = "10"
        ),
        onIntent = {}
    )
}

@Composable
private fun SetRow(
    modifier: Modifier = Modifier,
    setNumber: Int,
    workoutId: String,
    set: WorkoutSet,
    onIntent: (RoutineEvent) -> Unit
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

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            InputBox(
                modifier = Modifier.fillMaxWidth(0.55f).wrapContentHeight(),
                value = set.weight,
                onValueChange = { value ->
                    onIntent(RoutineEvent.UpdateSet(
                        workoutId = workoutId,
                        workoutSetId = set.uid,
                        weight = value
                    ))
                },
                placeholder = stringResource(R.string.weight_hint),
                showUnits = true,
                keyboardType = KeyboardType.Decimal,
                isError = set.isWeightError
            )
            InputBox(
                modifier = Modifier,
                placeholder = stringResource(R.string.reps_hint),
                value = set.reps,
                onValueChange = { value ->
                    onIntent(RoutineEvent.UpdateSet(
                        workoutId = workoutId,
                        workoutSetId = set.uid,
                        reps = value
                    ))
                },
                keyboardType = KeyboardType.Number,
                isError = set.isRepsError
            )
        }
    }
}