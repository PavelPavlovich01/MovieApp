package com.example.movieapp.data.model.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.data.model.dto.toGenreDvo
import com.example.movieapp.data.model.dvo.GenreDvo

@Entity(tableName = "genre")
data class GenreDbo(
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null,
    var movieId: Int? = null,
    var name: String? = null
)

fun GenreDbo.toGenreDvo() = GenreDvo(
        id = this.id,
        movieId = this.movieId,
        name = this.name
)
