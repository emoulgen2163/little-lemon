package com.example.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun OnBoarding(navController: NavController){
    val context = LocalContext.current

    val firstName by remember {
        mutableStateOf("")
    }

    val lastName by remember {
        mutableStateOf("")
    }

    val email by remember {
        mutableStateOf("")
    }

    fun validation(){

        if(firstName.isBlank() || lastName.isBlank() || email.isBlank()){
            Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
        } else{
            val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("first_name", firstName)
                putString("last_name", lastName)
                putString("user_email", email)
                apply()
            }

            Toast.makeText(context, "Registration successful.", Toast.LENGTH_SHORT).show()

            navController.navigate("Home")
        }
    }



    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.width(226.dp).height(56.dp),
            alignment = Alignment.Center,
            contentDescription = null,
            painter = painterResource(R.drawable.logo)
        )

        Spacer(modifier = Modifier.height(24.dp))


        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF435C52))
            .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center){
            Text(
                text = "Let's get to know you",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = firstName,
            onValueChange = {},
            label = { Text("First Name") })
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = lastName,
            onValueChange = {},
            label = { Text("Last Name") }
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = {},
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                validation()
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFFFD700)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Register", color = Color.Black)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview(){
    OnBoarding(
        navController = rememberNavController()
    )
}