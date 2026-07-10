package com.eelizarraras.workout.core.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.ui.theme.CardContentColor
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Composable
fun MainTopBar(
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    windowsInsets: WindowInsets
) {
    Content(
        title = title,
        windowsInsets = windowsInsets,
        onMenuClick = onMenuClick,
        onProfileClick = onProfileClick,
        modifier = modifier
    )
}

sealed class TopBarType(
    val icon: ImageVector,
    val description: String
) {
    object Normal: TopBarType(Icons.Default.Menu, "Menú")
    object Routine: TopBarType(Icons.AutoMirrored.Filled.ArrowBack, "Atras")
    object Workout: TopBarType(Icons.Default.Clear, "Cancelar")
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    title: String,
    type: TopBarType = TopBarType.Normal,
    windowsInsets: WindowInsets,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    // Nivel 2: Stateless
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(windowsInsets),
        color = Color(0xFF121212) // Color oscuro sólido similar a la imagen
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono de Menú
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = type.icon,
                    contentDescription = type.description,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Título de la App
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            // Imagen de Perfil (Circular con borde)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, CardContentColor.copy(alpha = 0.5f), CircleShape)
                    .clickable { onProfileClick() },
                contentAlignment = Alignment.Center
            ) {
                // Placeholder para la imagen de perfil
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(24.dp)
                )
                // Nota: En una implementación real, aquí se usaría AsyncImage de Coil
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1B1F)
@Composable
private fun MainTopBarPreview() {
    WorkoutTrackerTheme {
        MainTopBar(
            onMenuClick = {},
            onProfileClick = {},
            title = "IronMomentum",
            windowsInsets = WindowInsets()
        )
    }
}