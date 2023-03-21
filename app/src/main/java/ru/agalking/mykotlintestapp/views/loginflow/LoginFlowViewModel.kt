package ru.agalking.mykotlintestapp.views.loginflow

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.agalking.mykotlintestapp.data.users.local.database.UserDatabase
import ru.agalking.mykotlintestapp.data.users.local.entities.User
import ru.agalking.mykotlintestapp.repositories.UserRepository

class LoginFlowViewModel(application: Application): AndroidViewModel(application) {
    private val userRepository: UserRepository
    private var allUsers: MutableLiveData<User>? = null

    public var currentUser: MutableLiveData<User> = MutableLiveData<User>(User())

    public var firstNameErrorMessage: MutableLiveData<String> = MutableLiveData("")
    public var lastNameErrorMessage: MutableLiveData<String> = MutableLiveData("")
    public var emailErrorMessage: MutableLiveData<String> = MutableLiveData("")
    public var passwordErrorMessage: MutableLiveData<String> = MutableLiveData("")


    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        userRepository= UserRepository(userDao)
    }

    fun addUser(user: User) {
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

    fun getAllUsers(): LiveData<List<User>> = userRepository.getUserFlow.asLiveData()

    fun getUserByEmail(email: String): LiveData<User?> {
        val userList: LiveData<List<User?>> = userRepository.getUserFlowByEmail(email).asLiveData()
        val user = MutableLiveData<User?>()
        if (userList.value?.isEmpty() == false) {
            user.value = userList.value?.get(0)
        }
        return user
    }

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

    fun logInNewUser(user: User) : Boolean {
        if (getUserByEmail(user.email).isInitialized) {
            if (currentUser == null) {
                currentUser = MutableLiveData<User>()
            }
            currentUser?.value = user
            return true
        }
        return false
    }

}
