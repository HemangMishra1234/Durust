package com.tripod.durust.presentation.home

import com.tripod.durust.R
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.tripod.durust.presentation.home.individuals.DoctorSearch

@Composable
fun ConsultScreen(navController: NavController) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(scroll)
    ) {
        Surface(
            color = Color(0xFFBBDEFB),
            shape = RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                    ,verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Consult the specialists",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF212121)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Doctors at your assistance at a click.",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Get consulted right from home",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    ImageFrame(modifier = Modifier,
                        imgRes = R.drawable.nursesvg, // Replace with your image resource
                        width = 68,
                        height = 140
                    ){

                }
                }
            }
        }
        DoctorSearch()
    }
}