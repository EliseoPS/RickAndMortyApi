package com.example.rickandmortyapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.rickandmortyapp.models.CharacterResponse
import com.example.rickandmortyapp.services.CharacterService
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.rickandmortyapp.models.Result
import com.example.rickandmortyapp.utils.Screens


@Composable
fun HomeScreen(innerPadding : PaddingValues,navController: NavController) {
    val scope = rememberCoroutineScope()
    var characters by remember{
        mutableStateOf(listOf<Result>())
    }


    var isLoading by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        scope.launch {
            val BASE_URL = "https://rickandmortyapi.com/"
            val characterService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CharacterService::class.java)
            isLoading = true
            val response = characterService.getCharacters().results
            isLoading = false
            characters = response
            Log.i("Response", response.toString())
        }
    }
    if(isLoading){
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }
    else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFccd5ae)),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Rick and Morty API",
                modifier = Modifier
                    .padding(vertical = 5.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold

            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ){
                items(characters){
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                navController.navigate(Screens.CharacterDetail.route+"/${it.id}")
                            }
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            AsyncImage(
                                model = it.image,
                                contentDescription = "Imagen characcter",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(CircleShape)
                            )
                            Column(
                                verticalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = it.name,
                                    modifier = Modifier
                                        .padding(vertical = 10.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = it.species,
                                    modifier = Modifier
                                        .padding(vertical = 10.dp)
                                )
                            }


                        }
                    }
                }
            }
        }

    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview(){

    HomeScreen(innerPadding = PaddingValues(0.dp),navController = rememberNavController())

}


