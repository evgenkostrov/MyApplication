package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Api
import com.example.myapplication.Person
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MainViewModel : ViewModel() {
    private val api = Api()

//    private val str =
//        "{\"id\":2,\"name\":\"Morty Smith\",\"status\":\"Alive\",\"species\":\"Human\",\"type\":\"\",\"gender\":\"Male\",\"origin\":{\"name\":\"Earth (C-137)\",\"url\":\"https://rickandmortyapi.com/api/location/1\"},\"location\":{\"name\":\"Earth (Replacement Dimension)\",\"url\":\"https://rickandmortyapi.com/api/location/20\"},\"image\":\"https://rickandmortyapi.com/api/character/avatar/2.jpeg\",\"episode\":[\"https://rickandmortyapi.com/api/episode/1\",\"https://rickandmortyapi.com/api/episode/2\",\"https://rickandmortyapi.com/api/episode/3\",\"https://rickandmortyapi.com/api/episode/4\",\"https://rickandmortyapi.com/api/episode/5\",\"https://rickandmortyapi.com/api/episode/6\",\"https://rickandmortyapi.com/api/episode/7\",\"https://rickandmortyapi.com/api/episode/8\",\"https://rickandmortyapi.com/api/episode/9\",\"https://rickandmortyapi.com/api/episode/10\",\"https://rickandmortyapi.com/api/episode/11\",\"https://rickandmortyapi.com/api/episode/12\",\"https://rickandmortyapi.com/api/episode/13\",\"https://rickandmortyapi.com/api/episode/14\",\"https://rickandmortyapi.com/api/episode/15\",\"https://rickandmortyapi.com/api/episode/16\",\"https://rickandmortyapi.com/api/episode/17\",\"https://rickandmortyapi.com/api/episode/18\",\"https://rickandmortyapi.com/api/episode/19\",\"https://rickandmortyapi.com/api/episode/20\",\"https://rickandmortyapi.com/api/episode/21\",\"https://rickandmortyapi.com/api/episode/22\",\"https://rickandmortyapi.com/api/episode/23\",\"https://rickandmortyapi.com/api/episode/24\",\"https://rickandmortyapi.com/api/episode/25\",\"https://rickandmortyapi.com/api/episode/26\",\"https://rickandmortyapi.com/api/episode/27\",\"https://rickandmortyapi.com/api/episode/28\",\"https://rickandmortyapi.com/api/episode/29\",\"https://rickandmortyapi.com/api/episode/30\",\"https://rickandmortyapi.com/api/episode/31\",\"https://rickandmortyapi.com/api/episode/32\",\"https://rickandmortyapi.com/api/episode/33\",\"https://rickandmortyapi.com/api/episode/34\",\"https://rickandmortyapi.com/api/episode/35\",\"https://rickandmortyapi.com/api/episode/36\",\"https://rickandmortyapi.com/api/episode/37\",\"https://rickandmortyapi.com/api/episode/38\",\"https://rickandmortyapi.com/api/episode/39\",\"https://rickandmortyapi.com/api/episode/40\",\"https://rickandmortyapi.com/api/episode/41\"],\"url\":\"https://rickandmortyapi.com/api/character/2\",\"created\":\"2017-11-04T18:50:21.651Z\"}"
//    val person = Json { ignoreUnknownKeys = true }.decodeFromString(Person.serializer(), str)
//
//


    val person = MutableStateFlow<Person?>(null)

    fun loadPerson(personId:Long) {
        viewModelScope.launch {
            person.value = api.loadPerson(personId)
        }
    }

}