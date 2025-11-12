package com.example.socialnetworkapp.utli

interface Paginator<T> {

    suspend fun loadNextItems()
}