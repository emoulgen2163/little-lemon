package com.example.littlelemon

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import kotlin.text.category

class MainActivity : ComponentActivity() {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val database by lazy {
        Room.databaseBuilder(applicationContext, MenuDatabase::class.java, "database").build()
    }


    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val response = client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json").body<MenuNetwork>()
        return response.menu
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuDao().insertAll(*menuItemsRoom.toTypedArray())
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContent {
            LittleLemonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {

                    val navController = rememberNavController()
                    MyNavigation(navController = navController, context = this)

                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuDao().isEmpty()) {
                fetchMenu()
                saveMenuToDatabase(fetchMenu())
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun MenuItems(item: List<MenuItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp)
    ) {
        items(
            items = item,
            itemContent = { menuItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = menuItem.title,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.karla_regular))
                        )
                        Text(
                            text = menuItem.description,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.karla_regular))
                            )
                        Text(
                            text = "$%.2f".format(menuItem.price),
                            textAlign = TextAlign.Right,
                            color = Color(0xFFAFAFAF),
                            modifier = Modifier.padding(top = 4.dp),
                            fontFamily = FontFamily(Font(R.font.karla_regular))
                        )
                    }

                    GlideImage(
                        model = menuItem.image,
                        contentDescription = menuItem.title,
                        modifier = Modifier
                            .size(100.dp) // square thumbnail
                            .padding(start = 8.dp)
                    )
                }

            }
        )
    }
}
