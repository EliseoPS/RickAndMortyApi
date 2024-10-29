package com.example.rickandmortyapp.services
import com.example.rickandmortyapp.models.CharacterResponse
import com.example.rickandmortyapp.models.EpisodeResponse
import com.example.rickandmortyapp.models.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("api/character")
    suspend fun getCharacters() : CharacterResponse

    @GET("api/character/{id}")
    suspend fun getCharactersById(@Path("id") id:Int) : Result

    @GET("api/episode/{idEp}")
    suspend fun getEpisodeById(@Path("idEp") id:Int) : EpisodeResponse

}