package com.tripod.durust.presentation

import CircleRevealPager
import LiquidPagerScreen
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.ai.client.generativeai.Chat
import com.google.firebase.auth.FirebaseAuth
import com.tripod.durust.BakingScreen
import com.tripod.durust.BaseApplication
import com.tripod.durust.presentation.login.createaccount.CreateAccountEmailScreen
import com.tripod.durust.data.AlarmItem
import com.tripod.durust.data.AndroidAlarmSchedular
import com.tripod.durust.data.HealthConnectAvailability
import com.tripod.durust.data.HealthConnectManager
import com.tripod.durust.presentation.datacollection.ChatScreen
import com.tripod.durust.presentation.login.createaccount.CreateAccountPasswordScreen
import com.tripod.durust.presentation.login.createaccount.CreateAccountViewModel
import com.tripod.durust.presentation.login.createaccount.CreateAccountViewModelFactory
import com.tripod.durust.presentation.login.emailverification.EmailVerificationViewModel
import com.tripod.durust.presentation.login.emailverification.EmailVerificationViewModelFactory
import com.tripod.durust.presentation.login.emailverification.ForgotPassword
import com.tripod.durust.presentation.login.login.LogInScreen
import com.tripod.durust.presentation.login.emailverification.LogInSuccess
import com.tripod.durust.presentation.login.login.LoginViewModel
import com.tripod.durust.presentation.login.login.LoginViewModelFactory
import com.tripod.durust.presentation.login.emailverification.VerificationSuccess
import com.tripod.durust.presentation.login.emailverification.VerifyEmail
import com.tripod.durust.presentation.onboarding.MainScreen
import com.tripod.durust.presentation.onboarding.OnBoardingScreen
import com.tripod.durust.ui.theme.DurustTheme


class MainActivity : ComponentActivity() {

    companion object{

    var alarmItem: AlarmItem? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        // Access your API key as a Build Configuration variable
        super.onCreate(savedInstanceState)
        installSplashScreen()
        signOutIfEmailNotVerified()
        val schedular= AndroidAlarmSchedular(this)
        val healthConnectManager = (application as BaseApplication).healthConnectManager
        enableEdgeToEdge()
        setContent {
            DurustTheme {
                val auth = FirebaseAuth.getInstance()
                val navController = rememberNavController()
                val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(auth))
                val createAccountViewModel: CreateAccountViewModel = viewModel(factory = CreateAccountViewModelFactory(auth))
                val emailVerificationViewModel: EmailVerificationViewModel = viewModel(factory = EmailVerificationViewModelFactory(auth))
                NavHost(navController = navController, startDestination =if(FirebaseAuth.getInstance().currentUser != null)
                    NavTest
                    else NavOnBoardingScreen
                ) {
                    composable<NavTest>{
//                        TrackWeight(healthConnectManager)
//                        AlarmUI(schedular = schedular)
                        LogInSuccess(navController)
//                        VerificationSuccess(navController)
//                        BakingScreen()
//                        ChatScreen()
//                        CircleRevealPager()
//                        LiquidPagerScreen(context = this@MainActivity)
                    }

                    composable<NavCreateAccountEmailScreen>{
                        CreateAccountEmailScreen(viewModel = createAccountViewModel, navController = navController)
                    }
                    composable<NavCreateAccountPasswordScreen>{
                        CreateAccountPasswordScreen(viewModel = createAccountViewModel, navController = navController)
                    }
                    composable<NavOnBoardingScreen>{
                        OnBoardingScreen(navController)
                    }
                    composable<NavLoginScreen>(
                        enterTransition = {
                            slideInHorizontally(animationSpec = tween(300),
                            initialOffsetX = { fullWidth->fullWidth }
                            )
                        },
                        ){
                        LogInScreen(loginViewModel, navController)
                    }
                    composable<NavVerificationSuccessScreen>{
                        VerificationSuccess(navController)
                    }
                    composable<NavLoginSuccessScreen>{
                        LogInSuccess(navController)
                    }
                    composable<NavEmailVerificationScreen>{
                        val args = it.toRoute<NavEmailVerificationScreen>()
                        VerifyEmail(emailVerificationViewModel, navController, args.email, args.password)
                    }
                    composable<NavForgotPassword> { 
                        ForgotPassword(navController = navController)
                    }
                    composable<NavBakingScreen>(
                        enterTransition = {

                            expandHorizontally(animationSpec = tween(1000))
//                            slideInHorizontally(animationSpec = tween(1000),
//                                initialOffsetX = { fullWidth->fullWidth })
                        }
                            ){
                        BakingScreen()
                    }
                }
            }
        }
    }
}


@Composable
fun TestHome(healthConnectManager: HealthConnectManager,
             lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current){
    healthConnectManager.checkAvailabilty()
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver{_, event->
            healthConnectManager.checkAvailabilty()
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    if(healthConnectManager.availability.value == HealthConnectAvailability.NOT_INSTALLED){
        NotInstalledHealth()
    }else
    {
        Text(text = "${healthConnectManager.availability.value}")
    }
}

@Composable
fun NotInstalledHealth(){
    val url = Uri.parse("market://details")
        .buildUpon()
        .appendQueryParameter("id", "com.google.android.apps.healthdata")
        .appendQueryParameter("url", "healthconnect://onboarding")
        .build()
    val context = LocalContext.current

    Button(onClick = {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, url)
        )
    }) {
        Text(text = "Install App")
    }
}

fun signOutIfEmailNotVerified(){
    val auth = FirebaseAuth.getInstance()
    if(auth.currentUser != null && !auth.currentUser!!.isEmailVerified){
        auth.signOut()
    }
}

