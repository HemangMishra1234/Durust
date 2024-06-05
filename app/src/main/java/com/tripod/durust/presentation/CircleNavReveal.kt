package com.tripod.durust.presentation
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tripod.durust.BakingScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SemicircleAnimation(navController: NavController) {
    var isExpanded by remember { mutableStateOf(false) }
    val size by animateDpAsState(
        targetValue = if (isExpanded) 1000.dp else 50.dp, // Change size to expand
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    isExpanded = true
                },
            contentAlignment = Alignment.Center
        ) {
            // Additional content (like the arrow) can go here
        }
    }

    // Trigger navigation after the animation completes
    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            delay(1000) // Wait for the animation to complete
            withContext(Dispatchers.Main) {
                navController.navigate("nextScreen")
            }
        }
    }
}

@Composable
fun NextScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        // Content for the next screen
    }
}

@Composable
fun MyApp() {

    Surface {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "home") {
            composable("home") { SemicircleAnimation(navController) }
            composable("nextScreen") { BakingScreen() }
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    MyApp()
}
