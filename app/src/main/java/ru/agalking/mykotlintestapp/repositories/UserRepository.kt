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

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers() = userDao.deleteAllUsers()

    val getUserFlow: Flow<List<User>> get()
        = userDao.getUserFlow()
        .flowOn(Dispatchers.IO)
        .conflate()

    fun getUserByEmail(email: String): User?
        = userDao.getUserByEmail(email)

    fun getUserFlowByName(firstName: String, lastName: String): Flow<List<User>>
        = userDao.getUserFlowByName(firstName, lastName)
        .flowOn(Dispatchers.IO)
        .conflate()

}


