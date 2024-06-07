package com.tripod.durust.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tripod.durust.R
import com.tripod.durust.presentation.MainActivity
import com.tripod.durust.presentation.datacollection.GenderEntity
import com.tripod.durust.presentation.datacollection.NavChatScreen
import com.tripod.durust.presentation.home.individuals.DateMarkers
import com.tripod.durust.presentation.home.individuals.TaskList
import com.tripod.durust.presentation.home.individuals.TimeMarkers
import com.tripod.durust.ui.theme.bodyFontFamily
import com.tripod.durust.ui.theme.displayFontFamily

@Composable
fun Dashboard(navController: NavController, dashboardViewModel: DashboardViewModel) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(scroll)
    ) {
        UserGreetingCard(navController)
        DateMarkers()
        TimeMarkers()
        TaskList(dashboardViewModel)
        Row(Modifier.fillMaxWidth()) {
//            ImageFrame(modifier = Modifier, imgRes = R.drawable.drrundra) {
                
            }
        }
    }


@Composable
fun UserGreetingCard(navController: NavController) {

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp, start = 16.dp, end = 16.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .heightIn(min = 80.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Thank you for being kind to yourself!",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 21.sp,
                    fontFamily = displayFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )
        }
        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(78.dp, 16.dp, 16.dp, 0.dp),
            color = Color(0xFFAED5FF),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Hi! ${MainActivity.primaryUserData.value?.name}",
                style = TextStyle(
                    fontSize = 13.17.sp,
                    lineHeight = 26.35.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF454547),
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        AsyncImage(
            model =
                if (MainActivity.primaryUserData.value?.gender == GenderEntity.Female) R.drawable.girliconpng
                else R.drawable.malesong
            , // Replace with your image resource
            contentDescription = null,
            modifier = Modifier
                .padding(start = 24.dp)
                .size(68.dp)
                .clip(CircleShape)
                .clickable {
                    navController.navigate(NavChatScreen)
                },
            contentScale = ContentScale.Crop
        )


        Spacer(modifier = Modifier.height(16.dp))

    }
}