package ru.agalking.mykotlintestapp.data.users.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.agalking.mykotlintestapp.data.users.local.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getUserFlow(): Flow<List<User>>

    @Query("SELECT * FROM user_table WHERE email = :search")
    fun getUserFlowByEmail(search: String): Flow<List<User>>

    @Query("SELECT * FROM user_table WHERE first_name = :firstName  AND last_name = :lastName")
    fun getUserFlowByName(firstName: String, lastName: String): Flow<List<User>>

}
