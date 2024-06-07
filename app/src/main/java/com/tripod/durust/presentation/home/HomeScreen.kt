package com.tripod.durust.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.tripod.durust.R
import com.tripod.durust.presentation.chats.NavBotScreen
import com.tripod.durust.presentation.home.individuals.BottomNavigationItem
import kotlinx.serialization.Serializable

@Serializable
data class NavHomeScreen(
    val initialPage: Int = 0
)

@Composable
fun HomeScreen(
    initialPage: Int = 0,
    navController: NavController,
    dashboardViewModel: DashboardViewModel
) {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),

        BottomNavigationItem(
            title = "Tracker",
            image = R.drawable.baseline_bar_chart_24
        ),

        BottomNavigationItem(
            title = "Consult",
            image = R.drawable.baseline_medical_services_24
        ),
        BottomNavigationItem(
            title = "Library",
            image = R.drawable.baseline_menu_book_24
        )
    )
    var scrollRequired by remember {
        mutableStateOf<Int?>(null)
    }
    val state = rememberPagerState(initialPage = initialPage) {
        4
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = scrollRequired) {
        if (scrollRequired != null) {
            state.animateScrollToPage(scrollRequired!!)
            scrollRequired = null
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0xFFEEF6F8)),
            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFF7788F4),
                ) {
                    items.forEachIndexed { index, item ->

                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = Color.White,
                                selectedIconColor = Color(0xFF7788F4),
                                unselectedIconColor = Color(0xFFF7FBFF),
//                                indicatorColor = Color(0xFFAED5FF)
                            ),
                            selected = state.currentPage == index,
                            onClick = {
                                scrollRequired = index
                            },
                            label = {
                                Text(text = item.title)
                            },
                            alwaysShowLabel = false,

                            icon = {
                                if (item.image == null)
                                    Icon(
                                        imageVector = if (index == state.currentPage) {
                                            item.selectedIcon!!
                                        } else {
                                            item.unselectedIcon!!
                                        },
                                        contentDescription = item.title
                                    ) else {
                                    Icon(
                                        painter = painterResource(id = item.image),
                                        contentDescription = item.title
                                    )
                                }
                            }
                        )
                    }

                }
            }

        ) { paddingValues ->
            val pd = paddingValues
            Box(modifier = Modifier.fillMaxSize()) {

                HorizontalPager(state = state) {
                    when (it) {
                        0 -> {
                            Dashboard(navController, dashboardViewModel)
                        }

                        1 -> {
                            TrackerScreen(navController = navController, dashboardViewModel = dashboardViewModel, snackbarHostState = snackbarHostState)
                        }

                        2 -> {
                            ConsultScreen(navController)
                        }

                        3 -> {
                            LibraryScreen(navController)
                        }
                    }

                }
                Box(modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        navController.navigate(NavBotScreen())
                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.boticon),
                        contentDescription = null
                    )
                }
            }
        }
    }
}