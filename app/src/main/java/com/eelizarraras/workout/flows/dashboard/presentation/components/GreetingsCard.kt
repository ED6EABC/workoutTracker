package com.eelizarraras.workout.flows.dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eelizarraras.workout.R
import com.eelizarraras.workout.ui.theme.BlueCardBackground
import com.eelizarraras.workout.ui.theme.CardContentColor
import com.eelizarraras.workout.ui.theme.LightningBoltColor
import com.eelizarraras.workout.ui.theme.WorkoutTrackerTheme

@Composable
fun GreetingsCard() {
    val greetings = stringResource(R.string.greetings)
    val compliment = stringResource(R.string.compliment)
    Content(
        greetings = greetings,
        compliment = compliment
    )
}

@Composable
private fun Content(
    greetings: String,
    compliment: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BlueCardBackground,
            contentColor = CardContentColor
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
            ,
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = greetings,
                    modifier = Modifier.weight(2f),
                    fontSize = 42.sp,
                    lineHeight = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Icon(
                    imageVector = Icons.Default.ElectricBolt,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .weight(1f)
                        .rotate(10f),
                    tint = LightningBoltColor
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = compliment,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                color = CardContentColor.copy(alpha = 0.8f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingsCardPreview() {
    WorkoutTrackerTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            Content(
                greetings = stringResource(R.string.greetings),
                compliment = stringResource(R.string.compliment)
            )
        }
    }
}