package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun Profile(navController: NavController){

    val onBoarding: OnBoarding

    val context = LocalContext.current

    // Retrieve user data from SharedPreferences
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Read stored values
    val firstName = sharedPref.getString("first_name", "") ?: ""
    val lastName = sharedPref.getString("last_name", "") ?: ""
    val email = sharedPref.getString("email", "") ?: ""

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            alignment = Alignment.Center,
            contentDescription = null,
            painter = painterResource(R.drawable.logo)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            text = "Personal Information",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth().border(1.dp, Color.Black, RoundedCornerShape(8.dp)).padding(8.dp),
            text = "First Name: $firstName",
        )


        Spacer(modifier = Modifier.height(12.dp))


        Text(
            modifier = Modifier.fillMaxWidth().border(1.dp, Color.Black, RoundedCornerShape(8.dp)).padding(8.dp),
            text = "Last Name: $lastName",
        )

        Spacer(modifier = Modifier.height(12.dp))



        Text(
            modifier = Modifier.fillMaxWidth().border(1.dp, Color.Black, RoundedCornerShape(8.dp)).padding(8.dp),
            text = "Email: $email",
        )


        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                navController.navigate("OnBoarding")
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFFFD700)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Log Out", color = Color.Black)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    Profile(
        navController = rememberNavController()
    )
}