package ru.agalking.mykotlintestapp.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import ru.agalking.mykotlintestapp.data.users.local.dao.UserDao
import ru.agalking.mykotlintestapp.data.users.local.entities.User

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(user: User) : Long {
        return userDao.addUser(user)
    }

    suspend fun updateUser(user: User) : Int {
        return userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) : Int {
        return userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers() : Int {
        return userDao.deleteAllUsers()
    }

    val getAllUsers: Flow<List<User>> get()
        = userDao.getAllUsers()
        .flowOn(Dispatchers.IO)
        .conflate()

    fun getUserByEmail(email: String): User?
        = userDao.getUserByEmail(email)
 }


