package com.example.rickandmortyapp.models

data class CharacterResponse(
    val info: Info,
    val results: List<Result>
)