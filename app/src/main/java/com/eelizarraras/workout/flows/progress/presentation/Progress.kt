package com.eelizarraras.workout.flows.progress.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eelizarraras.workout.R
import com.eelizarraras.workout.core.data.model.entity.view.LoggedSetWithDetailsTuple
import com.eelizarraras.workout.core.domine.model.WorkoutUnit
import com.eelizarraras.workout.flows.progress.presentation.viewModel.ProgressViewModel
import com.eelizarraras.workout.ui.theme.DarkGreyCardBackground
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProgressScreen(
    paddingValues: PaddingValues,
    viewModel: ProgressViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Content(
        totalRoutines = uiState.totalRoutines,
        weeklyMinutes = uiState.weeklyActivityMinutes,
        personalRecords = uiState.personalRecords,
        selectedUnit = uiState.selectedUnit,
        onUnitSelected = { viewModel.selectUnit(it) },
        modifier = Modifier.padding(paddingValues)
    )
}

@Composable
private fun Content(
    totalRoutines: Int,
    weeklyMinutes: List<Int>,
    personalRecords: List<LoggedSetWithDetailsTuple>,
    selectedUnit: WorkoutUnit,
    onUnitSelected: (WorkoutUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Top Summary Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Entrenamientos
            StatCard(
                title = stringResource(R.string.workouts_label),
                value = totalRoutines.toString(),
                icon = Icons.Default.FitnessCenter,
                iconColor = TealAccent,
                modifier = Modifier.weight(1f)
            )
        }

        // Actividad semanal
        WeeklyActivityCard(weeklyMinutes = weeklyMinutes)

        // Record personal
        SectionHeader(
            title = stringResource(R.string.personal_records),
            actionText = ""
        )
        PersonalRecordsSection(
            records = personalRecords,
            selectedUnit = selectedUnit,
            onUnitSelected = onUnitSelected
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreyCardBackground)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.5f),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = if (title == stringResource(R.string.calories_label)) iconColor else Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun WeeklyActivityCard(weeklyMinutes: List<Int>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreyCardBackground)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.weekly_activity),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.sessions_per_day),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bar Chart
            val maxMinutes = weeklyMinutes.maxOrNull()?.coerceAtLeast(1) ?: 1
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                weeklyMinutes.forEach { minutes ->
                    val barHeightFraction = minutes.toFloat() / maxMinutes
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(24.dp)
                    ) {
                        if (minutes > 0) {
                            Text(
                                text = "${minutes}m",
                                fontSize = 10.sp,
                                color = TealAccent,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height((80 * barHeightFraction).dp)
                                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .background(TealAccent)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Days Labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("L", "M", "X", "J", "V", "S", "D").forEach { day ->
                    Text(
                        text = day,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.4f),
                        modifier = Modifier.width(24.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun PersonalRecordsSection(
    records: List<LoggedSetWithDetailsTuple>,
    selectedUnit: WorkoutUnit,
    onUnitSelected: (WorkoutUnit) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Filter Chips
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            WorkoutUnit.entries.forEach { unit ->
                val labelText = when (unit) {
                    WorkoutUnit.Kg -> "KG"
                    WorkoutUnit.Lbs -> "Lbs"
                    WorkoutUnit.Plates -> "Platos"
                }
                FilterChip(
                    selected = selectedUnit == unit,
                    onClick = { onUnitSelected(unit) },
                    label = { Text(labelText) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TealAccent,
                        selectedLabelColor = Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        if (records.isEmpty()) {
            Text(
                text = "No hay récords registrados para esta unidad",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            records.forEachIndexed { index, record ->
                val colors = listOf(Color(0xFF004D40), Color(0xFF311B92), Color(0xFFBF360C), Color(0xFF1A237E), Color(0xFF3E2723))
                val bgIconColor = colors[index % colors.size]
                
                val sdf = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
                val formattedDate = sdf.format(Date(record.date))

                val unitLabel = when (record.unit) {
                    WorkoutUnit.Kg -> "kg"
                    WorkoutUnit.Lbs -> "lbs"
                    WorkoutUnit.Plates -> "platos"
                }

                RecordItem(
                    name = record.exerciseName,
                    date = formattedDate,
                    value = if (record.weight % 1 == 0.0) record.weight.toInt().toString() else record.weight.toString(),
                    unit = unitLabel,
                    icon = Icons.Default.MilitaryTech,
                    iconBg = bgIconColor
                )
            }
        }
    }
}

@Composable
private fun RecordItem(
    name: String,
    date: String,
    value: String,
    unit: String,
    icon: ImageVector,
    iconBg: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreyCardBackground)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, style = MaterialTheme.typography.bodyLarge, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = date, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.5f))
            }
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    color = if (name == "Peso Muerto") Color(0xFFE58C71) else Color(0xFFC4D1FF),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = unit,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 4.dp, start = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun HistoryItem(
    title: String,
    time: String,
    kcal: String,
    date: String,
    icon: ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreyCardBackground)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.05f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = Color.White.copy(alpha = 0.6f))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge, color = Color.White, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.White.copy(alpha = 0.4f))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = time, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.4f))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Default.LocalFireDepartment, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.White.copy(alpha = 0.4f))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = kcal, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.4f))
                }
            }
            Text(text = date, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.4f))
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    actionText: String,
    onActionClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        if (actionText.isNotEmpty()) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.labelLarge,
                color = TealAccent,
                modifier = Modifier.clickable { onActionClick() }
            )
        }
    }
}

@Preview
@Composable
private fun ProgressScreenPreview() {
    WorkoutTrackerTheme {
        Content(
            totalRoutines = 5,
            weeklyMinutes = listOf(30, 45, 0, 60, 0, 90, 0),
            personalRecords = listOf(
                LoggedSetWithDetailsTuple(
                    exerciseName = "Pres de banco",
                    weight = 12.0,
                    unit = WorkoutUnit.Kg,
                    date = 1L
                )
            ),
            selectedUnit = WorkoutUnit.Kg,
            onUnitSelected = {}
        )
    }
}
