package com.tripod.durust.presentation.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import com.tripod.durust.R
import com.tripod.durust.presentation.chats.NavBotScreen
import com.tripod.durust.presentation.chats.data.BotUiState
import com.tripod.durust.presentation.home.individuals.rememberMarker
import com.tripod.durust.ui.theme.displayFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

@Composable
fun TrackerScreen(snackbarHostState: SnackbarHostState, navController: NavController, dashboardViewModel: DashboardViewModel) {
    var isPhysicalSelected by remember { mutableStateOf(true) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEF6F8))
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(Color(0xFFAED5FF))
                .statusBarsPadding()
        )
        {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .animateContentSize()
                ) {
                    ImageFrame(modifier = Modifier.padding(top = if(!isPhysicalSelected) 32.dp else 16.dp
                    ), imgRes = R.drawable.trackexerciseicon,
                        height = 60, width = 60) {
                        isPhysicalSelected = true
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    ImageFrame(modifier = Modifier.padding(top = if(isPhysicalSelected) 32.dp else 16.dp
                    ), imgRes = R.drawable.trackmentalhealthicon, height = 60, width = 60) {
                        isPhysicalSelected = false
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Chart()
            }

        }
        val scroll = rememberScrollState()
        Column(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 98.dp)
                .verticalScroll(scroll)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(Color(0xFFAED5FF))
                        .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
                        .fillMaxWidth(0.5f)
                        .background(
                            Color(0xFFEEF6F8)
                        )
                ) {
                    AnimatedVisibility(visible = isPhysicalSelected) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp))
                                    .background(Color(0xFFAED5FF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Physical wellness",

                                    // content 16 bold
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = displayFontFamily,
                                        fontWeight = FontWeight(500),
                                        color = Color.White,
                                    )
                                )
                            }
                            Column {
                                ImageFrame(
                                    Modifier.padding(16.dp, vertical = 16.dp),
                                    imgRes = R.drawable.food7
                                ) {
                                    Toast.makeText(context, "Currently we are working on this feature. So redirecting you to our chat screen", Toast.LENGTH_SHORT).show()
                                    navController.navigate(NavBotScreen(BotUiState.DE_CAROUSEL_STATE.name))
                                }
                                ImageFrame(
                                    Modifier.padding(16.dp),
                                    imgRes = R.drawable.desleep
                                ) {
                                    Toast.makeText(context, "Currently we are working on this feature. So redirecting you to our chat screen", Toast.LENGTH_SHORT).show()
                                    navController.navigate(NavBotScreen(BotUiState.DE_CAROUSEL_STATE.name))}
                                ImageFrame(
                                    Modifier.padding(16.dp),
                                    imgRes = R.drawable.deyourstats
                                ) {
                                    Toast.makeText(context, "Currently we are working on this feature. So redirecting you to our chat screen", Toast.LENGTH_SHORT).show()
                                    navController.navigate(NavBotScreen(BotUiState.DE_CAROUSEL_STATE.name))
                                }
                            }
                        }
                    }
                    AnimatedVisibility(visible = !isPhysicalSelected) {
                        Column {
                            ImageFrame(
                                Modifier.padding(16.dp, vertical = 16.dp),
                                imgRes = R.drawable.trackbook
                            ) {
                                Toast.makeText(context, "Currently we are working on this feature. So redirecting you to our chat screen", Toast.LENGTH_SHORT).show()
                                navController.navigate(NavBotScreen(BotUiState.DE_CAROUSEL_STATE.name))
                            }
                            ImageFrame(
                                Modifier.padding(16.dp, vertical = 16.dp),
                                imgRes = R.drawable.trackmovies
                            ) {
                                Toast.makeText(context, "Currently we are working on this feature. So redirecting you to our chat screen", Toast.LENGTH_SHORT).show()
                                navController.navigate(NavBotScreen(BotUiState.DE_CAROUSEL_STATE.name))
                            }
                            ImageFrame(
                                Modifier.padding(16.dp, vertical = 16.dp),
                                imgRes = R.drawable.treepng
                            ) {
                                Toast.makeText(context, "Currently we are working on this feature. So redirecting you to our chat screen", Toast.LENGTH_SHORT).show()
                                navController.navigate(NavBotScreen(BotUiState.DE_CAROUSEL_STATE.name))
                            }
                        }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(Color(0xFFAED5FF))
                        .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
                        .fillMaxWidth()
                        .background(
                            Color(0xFFEEF6F8)
                        )
                ) {
                    AnimatedVisibility(visible = !isPhysicalSelected) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp))
                                    .background(Color(0xFFAED5FF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Mental wellness",

                                    // content 16 bold
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = displayFontFamily,
                                        fontWeight = FontWeight(500),
                                        color = Color.White,
                                    )
                                )
                            }

                            Column {
                                ImageFrame(
                                    Modifier.padding(16.dp, vertical = 16.dp),
                                    imgRes = R.drawable.trackmusic
                                ) {
                                    Toast.makeText(context, "Currently we are working on this feature. So redirecting you to our chat screen", Toast.LENGTH_SHORT).show()
                                    navController.navigate(NavBotScreen(BotUiState.DE_CAROUSEL_STATE.name))
                                }
                                ImageFrame(
                                    Modifier.padding(16.dp),
                                    imgRes = R.drawable.trackbooks
                                ) {
                                    Toast.makeText(context, "Currently we are working on this feature. So redirecting you to our chat screen", Toast.LENGTH_SHORT).show()
                                    navController.navigate(NavBotScreen(BotUiState.DE_CAROUSEL_STATE.name))
                                }
                            }
                        }
                    }
                    AnimatedVisibility(visible = isPhysicalSelected) {
                        Column {
                            ImageFrame(
                                Modifier.padding(16.dp),
                                imgRes = R.drawable.dewatericon
                            ) {
                                Toast.makeText(context, "Currently we are working on this feature. So redirecting you to our chat screen", Toast.LENGTH_SHORT).show()
                                navController.navigate(NavBotScreen(BotUiState.DE_CAROUSEL_STATE.name))
                            }
                            ImageFrame(
                                Modifier.padding(16.dp),
                                imgRes = R.drawable.demedicine
                            ) {
                                Toast.makeText(context, "Currently we are working on this feature. So redirecting you to our chat screen", Toast.LENGTH_SHORT).show()
                                navController.navigate(NavBotScreen(BotUiState.DE_CAROUSEL_STATE.name))
                            }
                            ImageFrame(
                                Modifier.padding(16.dp),
                                imgRes = R.drawable.deexcercise
                            ) {
                                Toast.makeText(context, "Currently we are working on this feature. So redirecting you to our chat screen", Toast.LENGTH_SHORT).show()
                                navController.navigate(NavBotScreen(BotUiState.DE_CAROUSEL_STATE.name))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Chart(){
    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            modelProducer.tryRunTransaction {
                /* Learn more:
                https://patrykandpatrick.com/vico/wiki/cartesian-charts/layers/line-layer#data. */
                lineSeries { series(x, x.map { Random.nextFloat() * 15 }) }
            }
        }
    }
    ComposeChart1(modelProducer = modelProducer, modifier = Modifier)
}

@Composable
private fun ComposeChart1(modelProducer: CartesianChartModelProducer, modifier: Modifier) {
    val marker = rememberMarker()
    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberLineCartesianLayer(
                listOf(rememberLineSpec(DynamicShader.color(Color(0xffa485e0))))
            ),
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(guideline = null),
            persistentMarkers = mapOf(PERSISTENT_MARKER_X to marker),
        ),
        modelProducer = modelProducer,
        modifier = modifier,
        marker = marker,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}

private const val PERSISTENT_MARKER_X = 7f

private val x = (1..50).toList()