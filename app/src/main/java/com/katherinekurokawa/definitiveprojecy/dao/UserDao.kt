package com.katherinekurokawa.definitiveprojecy.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.katherinekurokawa.definitiveprojecy.entities.User

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user WHERE id=:userId")
    suspend fun getUserById(userId: Int): User

    @Query("SELECT * FROM user WHERE nameUser = :nameUserParameter AND passwordUser= :passwordUserParameter")
    suspend fun getUserByNameAndPassword(
        nameUserParameter: String,
        passwordUserParameter: String
    ): User

}