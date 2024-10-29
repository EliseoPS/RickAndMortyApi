package com.example.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import com.example.rickandmortyapp.screens.HomeScreen
import com.example.rickandmortyapp.utils.Screens
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.example.rickandmortyapp.screens.CharacterDetailScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            RickAndMortyAppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ){ innerPadding ->
                    NavHost(navController = navController, startDestination = Screens.Home.route){
                        composable(route = Screens.Home.route){
                            HomeScreen(innerPadding = innerPadding, navController = navController)
                        }
                        composable(
                            route = Screens.CharacterDetail.route+"/{characterId}",
                            arguments = listOf(
                                navArgument("characterId")
                                {
                                    type= NavType.IntType
                                }
                            )
                        )
                        {
                            val characterId = it.arguments?.getInt("characterId") ?: 0
                            CharacterDetailScreen(innerPadding = innerPadding, characterId = characterId,navController = navController)
                        }
                    }


                }
            }
        }

    }
}


