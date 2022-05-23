package com.example.androidroom.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meeptest.navigation.AnimationSplashScreen

@Composable
fun MeepNavigation(context : Context) {
    val navController =  rememberNavController()

    NavHost(navController = navController, startDestination = MeepTestScreens.SplashScreen.name){
        composable(MeepTestScreens.SplashScreen.name){
            AnimationSplashScreen(context)
        }
    }
}