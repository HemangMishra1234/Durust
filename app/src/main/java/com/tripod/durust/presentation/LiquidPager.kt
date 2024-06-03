package com.tripod.durust.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView


@OptIn(ExperimentalPagerApi::class)
@Composable
fun LiquidPagerCompose() {
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = 1){2}

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            LiquidPager(context).apply {
                adapter = object : androidx.viewpager2.widget.ViewPager2.Adapter() {
                    override fun getItemCount(): Int = 3

                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                        return object : RecyclerView.ViewHolder(FrameLayout(context)) {}
                    }

                    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                        val container = holder.itemView as FrameLayout
                        container.removeAllViews()
                        val page = when (position) {
                            0 -> PageOne()
                            1 -> PageTwo()
                            else -> PageThree()
                        }
                        container.addView(page)
                    }
                }
            }
        }
    )
}

@Composable
fun PageOne() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Page 1")
    }
}

@Composable
fun PageTwo() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Page 2")
    }
}

@Composable
fun PageThree() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Page 3")
    }
}
Step 3: Create the Adapter
Since we are using ViewPager2, the adapter needs to handle Compose views. Here is a custom adapter that integrates Compose:

ComposeViewAdapter.kt:

kotlin
Copy code
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView

class ComposeViewAdapter(private val pages: List<@Composable () -> Unit>) :
    RecyclerView.Adapter<ComposeViewAdapter.ComposeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposeViewHolder {
        val composeView = ComposeView(parent.context)
        return ComposeViewHolder(composeView)
    }

    override fun onBindViewHolder(holder: ComposeViewHolder, position: Int) {
        holder.composeView.setContent {
            pages[position]()
        }
    }

    override fun getItemCount(): Int = pages.size

    class ComposeViewHolder(val composeView: ComposeView) : RecyclerView.ViewHolder(composeView)
}
Step 4: Update LiquidPagerCompose to Use the Custom Adapter
Update LiquidPagerCompose to use the custom adapter:

MainActivity.kt (Updated):

kotlin
Copy code
@Composable
fun LiquidPagerCompose() {
    val context = LocalContext.current
    val pagerState = rememberPagerState()

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            LiquidPager(context).apply {
                adapter = ComposeViewAdapter(
                    listOf(
                        { PageOne() },
                        { PageTwo() },
                        { PageThree() }
                    )
                )
            }
        }
    )
}