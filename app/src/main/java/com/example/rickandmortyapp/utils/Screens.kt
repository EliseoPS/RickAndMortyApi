package com.example.rickandmortyapp.utils

sealed class Screens(val route : String) {
    data object Home : Screens("home")
    data object CharacterDetail : Screens("characterdetail")


}