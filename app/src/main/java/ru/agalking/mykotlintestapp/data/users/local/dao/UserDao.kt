package ru.agalking.mykotlintestapp.data.users.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.agalking.mykotlintestapp.data.users.local.entities.UserData

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: UserData)

    @Update
    suspend fun updateUser(user: UserData)

    @Delete
    suspend fun deleteUser(user: UserData)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): Flow<List<UserData>>

    @Query("SELECT * FROM user_table WHERE email = :search")
    fun findByEmail(search: String): Flow<List<UserData>>

    @Query("SELECT * FROM user_table WHERE first_name = :firstName  AND last_name = :lastName")
    fun findByName(firstName: String, lastName: String): Flow<List<UserData>>

}
