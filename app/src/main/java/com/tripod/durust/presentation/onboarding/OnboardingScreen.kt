package com.tripod.durust.presentation.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.tripod.durust.R
import com.tripod.durust.presentation.NavLoginScreen
import com.tripod.durust.presentation.login.createaccount.AppButton
import com.tripod.durust.ui.theme.bodyFontFamily
import com.tripod.durust.ui.theme.displayFontFamily



@Composable
fun OnBoardingScreen(navController: NavController){
    var screen by remember {
        mutableIntStateOf(1)
    }
    Surface() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.onBoardingBackground))
        ) {
            Logo(modifier = Modifier
                .offset(30.dp,50.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(160.dp))
                AnimatedCenterBox(screen = screen,modifier = Modifier
                    .align(Alignment.End)
                    .fillMaxWidth())
            }
            Column(
                modifier = Modifier.align(Alignment.BottomCenter
                )) {
                AppButton(text = "Next", isEnabled = true){
                    when(screen){
                        1->{screen++}
                        2->screen++
                        3->{
                            navController.navigate(NavLoginScreen)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

@Composable
private fun Logo(modifier: Modifier){
    Image(painter = painterResource(id = R.drawable.logo__name__black),
        contentDescription = null,
        modifier = modifier
            .padding(1.dp)
            .width(77.dp)
            .height(64.dp)
        )
}


@Composable
private fun ImageGroupS1(modifier: Modifier){
    Image(painter = painterResource(id = R.drawable.doctorsgroup),
        contentDescription = null,
        modifier = modifier
            .padding(1.dp)
            .width(245.dp)
            .height(206.dp)
    )
}

@Composable
private fun GroupCenterS1(modifier: Modifier){
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (back, group) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.flows1), contentDescription = null,
                modifier = Modifier
                    .constrainAs(back){
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                contentScale = ContentScale.FillBounds
            )

            ImageGroupS1(
                modifier = Modifier
                    .constrainAs(group){
                        bottom.linkTo(parent.bottom, margin = 68.dp)
                        end.linkTo(parent.end, margin = 48.dp)
                }
            )
        }
    }
}

@Composable
private fun GroupCenterS2(modifier: Modifier){
    Box(modifier = modifier.fillMaxWidth()){
        Image(painter = painterResource(id = R.drawable.backs2), contentDescription = null,
            modifier=Modifier.fillMaxWidth())
        Image(painter = painterResource(id = R.drawable.pillsgroup), contentDescription = null,
            modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun GroupCenterS3(modifier: Modifier){
    Box(modifier = modifier){
        Image(painter = painterResource(id = R.drawable.backs3), contentDescription = null)
        Image(painter = painterResource(id = R.drawable.run_group), contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .height(250.dp)
                .width(300.dp))
    }
}

@Composable
private fun NonAnimatedCenterS1(modifier: Modifier) {
    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        GroupCenterS1(
            modifier = modifier
        )
        Image(
            painter = painterResource(id = R.drawable.dots11),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome to Durust!",
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 48.sp,
                fontFamily = displayFontFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF454547),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your ultimate companion for getting better and achieving optimal health. Let’s begin your journey to wellness!",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFF6F6D6D),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.width(330.dp)
        )
    }
}

@Composable
private fun NonAnimatedCenterS2(modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GroupCenterS2(
            modifier = modifier
        )
        Image(
            painter = painterResource(id = R.drawable.dots1),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Track your Health ",
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 48.sp,
                fontFamily = displayFontFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF454547),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Monitor your vitals, set health goals, and keep an eye on your overall well-being. ",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFF6F6D6D),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.width(330.dp)
        )
    }
}


@Composable
private fun NonAnimatedCenterS3(modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GroupCenterS3(
            modifier = modifier
        )
        Image(
            painter = painterResource(id = R.drawable.dots3),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Fitness Plans",
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 48.sp,
                fontFamily = displayFontFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF454547),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Discover workout routines tailored to your fitness level and goals. ",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 21.sp,
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFF6F6D6D),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.width(330.dp)
        )
    }
}

@Composable
private fun AnimatedCenterBox(screen: Int, modifier: Modifier){
    Box {
        AnimatedContent(
            targetState = screen,
            label = "",
            transitionSpec = {
                slideIntoContainer(
                    animationSpec = tween(100, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                ).togetherWith(
                    slideOutOfContainer(
                        animationSpec = tween(100, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.Left
                    )
                )
            }
        ) { targetState ->
            when (targetState) {
                1 -> NonAnimatedCenterS1(modifier = modifier)
                2 -> NonAnimatedCenterS2(modifier = modifier)
                3 -> NonAnimatedCenterS3(modifier = modifier)
            }
        }
    }
}
