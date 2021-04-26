package com.example.movieapp.data.database

import androidx.room.*
import com.example.movieapp.data.model.dbo.GenreDbo

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre")
    suspend fun getAllGenres(): List<GenreDbo>?

    @Query("DELETE FROM genre")
    suspend fun deleteAll()

    @Update
    suspend fun update(genre: GenreDbo)

    @Insert
    suspend fun insert(genre: GenreDbo)

    @Delete()
    suspend fun delete(genre: GenreDbo)
}