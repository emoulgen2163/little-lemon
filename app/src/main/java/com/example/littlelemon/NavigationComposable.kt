
package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyNavigation(navController: NavHostController, context: Context){

    val sharedPreferences: SharedPreferences = remember {
        context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    }

    val isRegistered = sharedPreferences.contains("first_name") &&
            sharedPreferences.contains("last_name") &&
            sharedPreferences.contains("user_email")
    val startDestination = if (isRegistered) Home.route else OnBoarding.route


    NavHost(navController = navController, startDestination = startDestination) {

        composable(OnBoarding.route) {
            OnBoarding(navController)
        }

        composable(Home.route) {
            Home(navController)
        }

        composable(Profile.route) {
            Profile(navController)
        }

    }
}
