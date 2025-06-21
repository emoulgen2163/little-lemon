package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.compose.foundation.border

@Composable

fun Home(navController: NavController){

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.width(226.dp).height(56.dp),
                alignment = Alignment.Center,
                contentDescription = null,
                painter = painterResource(R.drawable.logo)
            )

            Image(
                modifier = Modifier.height(50.dp).clickable(
                    enabled = true,
                    onClick = { navController.navigate("Profile") }
                ),
                alignment = Alignment.TopEnd,
                contentDescription = null,
                painter = painterResource(R.drawable.profile)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF435C52))
                .padding(16.dp),
        ) {

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Little Lemon",
                        color = Color(0xFFF4CE14),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.markazitext_regular))
                    )

                    Text(
                        text = "Chicago",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.markazitext_regular))
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.description),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.karla_regular))
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Image(
                    painter = painterResource(id = R.drawable.hero_image), // Replace with your image
                    contentDescription = "Dish Image",
                    modifier = Modifier.
                    height(100.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

        }
        ShowMenuItems()
    }
}

@Composable
fun CategoryChips(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        categories.forEach { category ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (selectedCategory == category) Color(0xFF495E57) else Color(0xFFAFAFAF)
                    )
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                Text(
                    text = category,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.karla_regular)),
                    fontWeight = FontWeight(800),
                    color = if (selectedCategory == category) Color.White else Color.Black
                )
            }
        }
    }
}


@Composable
fun ShowMenuItems(){

    val context = LocalContext.current

    val database by lazy {
        Room.databaseBuilder(context, MenuDatabase::class.java, "database").build()
    }

    val databaseMenuItems = database.menuDao().getAll().observeAsState(emptyList()).value

    val categories = listOf("All", "Starters", "Mains", "Desserts", "Drinks")
    var selectedCategory by remember { mutableStateOf("All") }


    var orderMenuItems by remember {
        mutableStateOf(false)
    }

    val menuItems = if(orderMenuItems){
        databaseMenuItems.sortedBy {
            it.title
        }
    } else {
        databaseMenuItems
    }

    var searchPhrase by remember {
        mutableStateOf("")
    }


    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        onValueChange = {
            searchPhrase = it
        },
        label = { Text("Enter search phrase") },
        value = searchPhrase,
        leadingIcon = { Icon( imageVector = Icons.Default.Search, contentDescription = null) }
    )

    Text(
        text = "ORDER FOR DELIVERY!",
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily(Font(R.font.karla_regular)),
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        color = Color.Black
    )

    CategoryChips(
        categories = categories,
        selectedCategory = selectedCategory,
        onCategorySelected = { selectedCategory = it }
    )

    val filteredItems = menuItems.filter {
        val matchesSearch = it.title.contains(searchPhrase, ignoreCase = true)
        val matchesCategory = selectedCategory == "All" || it.category.equals(selectedCategory, ignoreCase = true)
        matchesSearch && matchesCategory
    }

    MenuItems(filteredItems)
}




@Preview(showBackground = true)
@Composable
fun HomePreview(){
    Home(rememberNavController())
}