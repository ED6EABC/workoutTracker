package com.eelizarraras.workout.core.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.core.presentation.model.BottomBarScreen
import com.eelizarraras.workout.ui.theme.TealAccent
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Composable
fun MainNavBar(
    currentScreen: BottomBarScreen,
    onNavigate: (BottomBarScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    // Nivel 1: Stateful
    val items = listOf(
        NavigationItem(
            label = "Dashboard",
            icon = Icons.Default.Dashboard,
            screen = BottomBarScreen.Dashboard
        ),
        NavigationItem(
            label = "Routines",
            icon = Icons.Default.FitnessCenter,
            screen = BottomBarScreen.Routine
        ),
        NavigationItem(
            label = "Progress",
            icon = Icons.Default.Timeline,
            screen = BottomBarScreen.Workout
        )
    )

    Content(
        items = items,
        currentScreen = currentScreen,
        onItemClick = onNavigate,
        modifier = modifier
    )
}

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val screen: BottomBarScreen
)

@Composable
fun Content(
    items: List<NavigationItem>,
    currentScreen: BottomBarScreen,
    onItemClick: (BottomBarScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    // Nivel 2: Stateless
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        color = Color(0xFF121212) // Fondo oscuro sólido
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = item.screen == currentScreen
                
                NavBarItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onItemClick(item.screen) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun NavBarItem(
    item: NavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) TealAccent else Color.Transparent,
        label = "backgroundColor"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF1C1B1F) else Color.White.copy(alpha = 0.6f),
        label = "contentColor"
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(vertical = 12.dp, horizontal = 4.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = item.label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                ),
                color = contentColor
            )
        }
    }
}

@Preview
@Composable
private fun MainNavBarPreview() {
    WorkoutTrackerTheme {
        MainNavBar(
            currentScreen = BottomBarScreen.Dashboard,
            onNavigate = {}
        )
    }
}