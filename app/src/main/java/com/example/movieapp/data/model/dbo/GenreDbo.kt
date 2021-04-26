package com.example.movieapp.data.model.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class GenreDbo(
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null,
    var movieId: Int? = null,
    var name: String? = null
)