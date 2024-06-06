//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Rect
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.geometry.center
//import androidx.compose.ui.graphics.*
//import androidx.compose.ui.input.pointer.pointerInteropFilter
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.Density
//import androidx.compose.ui.unit.LayoutDirection
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
////import com.tripod.durust.BakingScreen
//import com.tripod.durust.R
//import com.tripod.durust.presentation.login.emailverification.VerificationSuccess
//import kotlin.math.absoluteValue
//import kotlin.math.sqrt
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun CircleRevealPager() {
//    val state = rememberPagerState(){
//        2
//    }
//    var offsetY by remember { mutableStateOf(0f) }
//
//    HorizontalPager(
//        state = state,
//        modifier = Modifier
//            .pointerInteropFilter {
//                offsetY = it.y
//                false
//            }
//            .clip(RoundedCornerShape(25.dp))
////            .background(Color.Black)
//    ) { page ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .graphicsLayer {
//                    val pageOffset = (state.currentPage - page + state.currentPageOffsetFraction).absoluteValue
//
//                    if (page > state.currentPage) {
//                        shadowElevation = 20f
//                        shape = CirclePath(
//                            progress = 1f - pageOffset,
//                            origin = Offset(
//                                size.width,
//                                offsetY,
//                            )
//                        )
//                        clip = true
//                        val scale = 1f + (pageOffset * 0.4f)
//                        scaleX = scale
//                        scaleY = scale
//
//                        alpha = 1f - pageOffset
//                    } else if (page == state.currentPage) {
//                        shadowElevation = 0f
//                        shape = RectangleShape
//                        clip = false
//                        scaleX = 1f
//                        scaleY = 1f
//                        alpha = 1f
//                    }
//                },
//            contentAlignment = Alignment.Center,
//        ) {
//            when(page){
////                0-> VerificationSuccess()
//                1-> BakingScreen()
//            }
//        }
//    }
//}
//
//class CirclePath(private val progress: Float, private val origin: Offset = Offset(0f, 0f)) : Shape {
//    override fun createOutline(
//        size: Size, layoutDirection: LayoutDirection, density: Density
//    ): Outline {
//
//        val center = Offset(
//            x = size.center.x - ((size.center.x - origin.x) * (1f - progress)),
//            y = size.center.y - ((size.center.y - origin.y) * (1f - progress)),
//        )
//        val radius = (sqrt(
//            size.height * size.height + size.width * size.width
//        ) * 0.5f) * progress
//
//        return Outline.Generic(Path().apply {
//            addOval(
//                Rect(
//                    center = center,
//                    radius = radius,
//                )
//            )
//        })
//    }
//}
//
//data class Destination(
//    val location: String,
//    val description: String,
//)
//
//val destinations = listOf(
//    Destination(
//        "Bali",
//        "Known for its beautiful beaches, stunning temples, and lush greenery, Bali is a tropical paradise that offers a unique blend of natural beauty and cultural experiences. It's an ideal destination for those seeking a relaxing vacation filled with yoga, spa treatments, and sunsets."
//    ),
//    Destination(
//        "Santorini",
//        "A picturesque island in the Aegean Sea, Santorini is famous for its white-washed buildings, blue-domed churches, and stunning sunsets. It's a perfect destination for honeymooners or those looking for a romantic getaway."
//    ),
//    Destination(
//        "Tokyo",
//        "A bustling city that seamlessly blends tradition and modernity, Tokyo offers an exciting vacation experience that's both vibrant and unique. From traditional Japanese gardens and temples to modern shopping districts and futuristic technology, there's something for everyone in Tokyo."
//    ),
//    Destination(
//        "Machu Picchu",
//        "A UNESCO World Heritage Site, Machu Picchu is an ancient Incan citadel situated high in the Andes Mountains. It's a destination for adventure seekers, as hiking the Inca Trail is the only way to reach the site."
//    ),
//    Destination(
//        "Banff",
//        "A stunning national park in the Canadian Rockies, Banff is a year-round destination that offers scenic vistas, crystal-clear lakes, and abundant wildlife. It's an ideal spot for nature lovers, hikers, and skiers."
//    ),
//    Destination(
//        "Marrakech",
//        "A vibrant city known for its bustling markets, colorful architecture, and rich cultural heritage, Marrakech is an exotic destination that offers a unique blend of ancient and modern experiences."
//    ),
//    Destination(
//        "The Maldives",
//        "An island paradise located in the Indian Ocean, the Maldives is known for its turquoise waters, white sandy beaches, and overwater bungalows. It's an ideal destination for those seeking a luxurious and romantic getaway."
//    ),
//    Destination(
//        "Sydney",
//        "A cosmopolitan city situated on a beautiful harbor, Sydney is a destination that offers both natural beauty and urban sophistication. It's an ideal spot for beachgoers, foodies, and culture lovers."
//    ),
//    Destination(
//        "Maui",
//        "A tropical island paradise in the Pacific Ocean, Maui is known for its beautiful beaches, scenic drives, and outdoor activities such as surfing, snorkeling, and hiking. It's an ideal destination for those seeking a laid-back vacation filled with sunshine and relaxation."
//    ),
//    Destination(
//        "Venice",
//        "A romantic city built on a series of canals, Venice is known for its stunning architecture, art, and history. It's an ideal destination for culture lovers, foodies, and those seeking a romantic getaway."
//    ),
//)
