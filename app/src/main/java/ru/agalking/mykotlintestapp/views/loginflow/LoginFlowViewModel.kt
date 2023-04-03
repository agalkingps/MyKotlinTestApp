package ru.agalking.mykotlintestapp.views.loginflow

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.agalking.mykotlintestapp.data.users.local.database.UserDatabase
import ru.agalking.mykotlintestapp.data.users.local.entities.User
import ru.agalking.mykotlintestapp.repositories.UserRepository

class LoginFlowViewModel(application: Application): AndroidViewModel(application) {
    private val userRepository: UserRepository

    var currentUser: MutableLiveData<User> = MutableLiveData<User>(User())

    var firstNameErrorMessage: MutableLiveData<String> = MutableLiveData("")
    var lastNameErrorMessage: MutableLiveData<String> = MutableLiveData("")
    var emailErrorMessage: MutableLiveData<String> = MutableLiveData("")
    var passwordErrorMessage: MutableLiveData<String> = MutableLiveData("")


    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        userRepository= UserRepository(userDao)
//        deleteAllUsers()
    }

    fun addUser(user: User) {
        user.id = 0
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteUser(user)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteAllUsers()
        }
    }

    fun getAllUsers() : Flow<List<User>> = userRepository.getUserFlow

    suspend fun getUserByEmail(email: String): User? =
            userRepository.getUserByEmail(email)

    fun getUserByName(firstName: String, lastName: String): LiveData<User?> {
        val userList: LiveData<List<User?>> = userRepository.getUserFlowByName(firstName, lastName).asLiveData()
        val user = MutableLiveData<User?>()
        if (userList.value?.isEmpty() == false) {
            user.value = userList.value?.get(0)
        }
        return user
    }

    fun signInNewUser(user: User) : Boolean {
        try {
            addUser(user)
            if (currentUser == null) {
                currentUser = MutableLiveData<User>()
            }
            currentUser?.value = user
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            //Код, который выполняется при возникновении исключения
        }
        return false
    }

    fun logInUser(user: User): Boolean {
        var res: Boolean = false

        viewModelScope.launch {
            val foundUser = getUserByEmail(user.email)
            if (foundUser != null) {
                currentUser.value = foundUser!!
                res = true
            }
        }
        return res
    }

}
/*
viewModelScope.launch {
    tapCount++
    // suspend this coroutine for one second
    delay(1_000)
    // resume in the main dispatcher
    // _snackbar.value can be called directly from main thread
    _taps.postValue("$tapCount taps")
}
*/