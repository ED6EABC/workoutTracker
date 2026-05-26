package com.eelizarraras.workout.flows.progress.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.MonitorWeight
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
import com.eelizarraras.workout.R
import com.eelizarraras.workout.ui.theme.DarkGreyCardBackground
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Composable
fun ProgressScreen(
    modifier: Modifier = Modifier
) {
    // Nivel 1: Stateful
    Content(
        modifier = modifier
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier
) {
    // Nivel 2: Stateless
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
            StatCard(
                title = stringResource(R.string.workouts_label),
                value = "24",
                icon = Icons.Default.FitnessCenter,
                iconColor = TealAccent,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = stringResource(R.string.calories_label),
                value = "8,500 kcal",
                icon = Icons.Default.LocalFireDepartment,
                iconColor = Color(0xFFE58C71),
                modifier = Modifier.weight(1f)
            )
        }

        StatCard(
            title = stringResource(R.string.total_volume_label),
            value = "12,400 kg",
            icon = Icons.Default.MonitorWeight,
            iconColor = Color(0xFFC4D1FF),
            modifier = Modifier.fillMaxWidth()
        )

        // 2. Weekly Activity Chart
        WeeklyActivityCard()

        // 3. Personal Records
        SectionHeader(
            title = stringResource(R.string.personal_records),
            actionText = ""
        )
        PersonalRecordsSection()

        // 4. Recent History
        SectionHeader(
            title = stringResource(R.string.recent_history),
            actionText = stringResource(R.string.see_all),
            onActionClick = { /* Ver Todo */ }
        )
        RecentHistorySection()
        
        Spacer(modifier = Modifier.height(24.dp))
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
private fun WeeklyActivityCard() {
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
                
                // Trend Badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = TealAccent.copy(alpha = 0.15f),
                    modifier = Modifier.height(28.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = TealAccent,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "+12%",
                            color = TealAccent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // Placeholder for Chart

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
                        modifier = Modifier.width(20.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun PersonalRecordsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Filter Chips
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = true,
                onClick = {},
                label = { Text("KG") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = TealAccent,
                    selectedLabelColor = Color.Black
                ),
                shape = RoundedCornerShape(16.dp)
            )
            FilterChip(selected = false, onClick = {}, label = { Text("Lbs") })
            FilterChip(selected = false, onClick = {}, label = { Text("Platos") })
        }

        RecordItem(
            name = "Sentadilla (Back Squat)",
            date = "15 Oct, 2023",
            value = "140",
            unit = "kg",
            icon = Icons.Default.MilitaryTech,
            iconBg = Color(0xFF004D40)
        )
        RecordItem(
            name = "Press de Banca",
            date = "02 Nov, 2023",
            value = "100",
            unit = "kg",
            icon = Icons.Default.MilitaryTech,
            iconBg = Color(0xFF311B92)
        )
        RecordItem(
            name = "Peso Muerto",
            date = "28 Oct, 2023",
            value = "180",
            unit = "kg",
            icon = Icons.Default.MilitaryTech,
            iconBg = Color(0xFFBF360C)
        )
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
private fun RecentHistorySection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HistoryItem(
            title = "Push Day - Hipertrofia",
            time = "54m",
            kcal = "420 kcal",
            date = "Ayer",
            icon = Icons.Default.CalendarMonth
        )
        HistoryItem(
            title = "Pull Day + Cardio",
            time = "68m",
            kcal = "510 kcal",
            date = "8 Nov",
            icon = Icons.AutoMirrored.Filled.DirectionsRun
        )
        HistoryItem(
            title = "Leg Day Brutal",
            time = "45m",
            kcal = "380 kcal",
            date = "6 Nov",
            icon = Icons.Default.CalendarMonth
        )
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
        ProgressScreen()
    }
}
