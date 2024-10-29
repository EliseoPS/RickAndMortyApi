package com.example.rickandmortyapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.rickandmortyapp.models.Location
import com.example.rickandmortyapp.models.Origin
import com.example.rickandmortyapp.models.Result
import com.example.rickandmortyapp.services.CharacterService
import com.example.rickandmortyapp.utils.Screens
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun CharacterDetailScreen(innerPadding : PaddingValues, characterId : Int,navController: NavController) {
    val scope = rememberCoroutineScope()
    var character by remember {
        mutableStateOf(
            Result(
                created = "",
                episode = listOf(),
                gender = "",
                id = 0,
                image = "",
                location = Location("", ""),
                name = "",
                origin = Origin("", ""),
                species = "",
                status = "",
                type = "",
                url = ""
            )
        )
    }
    var episodeNames by remember { mutableStateOf(listOf<String>()) }


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
            val response = characterService.getCharactersById(characterId)
            character = response

            // Obtener nombres de episodios
            val episodeIds = character.episode.map { it.split("/").last().toInt() }
            val episodeNamesList = mutableListOf<String>()
            for (id in episodeIds) {
                val episodeResponse = characterService.getEpisodeById(id)
                episodeNamesList.add(episodeResponse.name)
            }
            episodeNames = episodeNamesList
            isLoading = false

            Log.i("Response", response.toString())
        }
    }
    if (isLoading) {
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFccd5ae)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = character.image,
                        contentDescription = "Imagen character",
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = character.name,
                            modifier = Modifier.padding(vertical = 10.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )

                        Row {
                            Text(
                                text = "Estado: ",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                            Text(
                                text = character.status,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }

                        Row {
                            Text(
                                text = "Especie: ",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                            Text(
                                text = character.species,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Creado: ",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                            Text(
                                text = character.created,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.LightGray)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(
                                text = "Gender:",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = character.gender
                            )
                        }

                    }

                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.LightGray)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(
                                text = "Origen:",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = character.origin.name
                            )
                        }

                    }

                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.LightGray)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(
                                text = "Localización:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = character.location.name
                            )
                        }

                    }

                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ){
                    Text(
                        text = "Episodios:",
                        fontWeight = FontWeight.Bold
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(8.dp)
                        .heightIn(max = 220.dp)

                ) {
                    items(episodeNames) { episodeName ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)

                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.LightGray)
                        ) {
                            Text(text = episodeName, modifier = Modifier.padding(2.dp))

                        }
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF606c38))
                        .clickable {
                            navController.navigate(Screens.Home.route)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Volver",
                        color = Color.White
                    )
                }


            }


        }


    }
}