package ru.agalking.mykotlintestapp.repositories

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ru.agalking.mykotlintestapp.data.users.local.dao.UserDao
import ru.agalking.mykotlintestapp.data.users.local.entities.UserData

class UserRepository(private val userDao: UserDao) {

    suspend fun getAllUsers(): Flow<List<UserData>> = userDao.readAllData()

    suspend fun findUserByEmail(email: String): Flow<List<UserData>> = userDao.findByEmail(email)

    suspend fun findUserByName(firstName: String, lastName: String): Flow<List<UserData>> = userDao.findByName(firstName, lastName)

    suspend fun addUser(user: UserData) {
        userDao.addUser(user)
    }

    suspend fun updateUser(user: UserData) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: UserData) {
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}
