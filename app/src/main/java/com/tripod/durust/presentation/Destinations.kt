package com.tripod.durust.presentation

import kotlinx.serialization.Serializable

@Serializable
object NavTest

@Serializable
object NavCreateAccountEmailScreen

@Serializable
object NavCreateAccountPasswordScreen

@Serializable
object NavVerificationSuccessScreen

@Serializable
object NavLoginScreen

@Serializable
object NavLoginSuccessScreen

@Serializable
object NavOnBoardingScreen

@Serializable
data class NavEmailVerificationScreen(
    val email: String,
    val password: String
)

@Serializable
object NavBakingScreen

@Serializable
object NavForgotPassword