package ru.agalking.mykotlintestapp.views.loginflow

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.agalking.mykotlintestapp.R
import ru.agalking.mykotlintestapp.data.users.local.database.UserDatabase
import ru.agalking.mykotlintestapp.data.users.local.entities.User
import ru.agalking.mykotlintestapp.repositories.UserRepository
import ru.agalking.mykotlintestapp.utils.Event

class LoginFlowViewModel(application: Application): AndroidViewModel(application) {
    private val userRepository: UserRepository
    private val appContext: Context = application

    var currentUser = MutableLiveData<User>(User())
    val statusMessage = MutableLiveData<Event<String>>()
    val signInCompletion = MutableLiveData<Boolean>(false)
    val loginCompletion = MutableLiveData<Boolean>(false)

    var firstNameErrorMessage = MutableLiveData<String>("")
    var lastNameErrorMessage = MutableLiveData<String>("")
    var emailErrorMessage = MutableLiveData<String>("")
    var passwordErrorMessage = MutableLiveData<String>("")


    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        userRepository= UserRepository(userDao)
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

    fun getAllUsers() : Flow<List<User>> = userRepository.getAllUsers

    fun getUserByEmail(email: String): User? =
            userRepository.getUserByEmail(email)

    fun signInNewUser(user: User) {
        try {
            if (signInInputCheck()) {
                viewModelScope.launch(Dispatchers.IO) {
                    user.id = 0
                    val newRowId: Long = userRepository.addUser(user)
                    withContext(Dispatchers.Main) {
                        if (newRowId > -1) {
                            if (currentUser == null) {
                                currentUser = MutableLiveData<User>()
                            }
                            currentUser?.value = user
                            signInCompletion.value = true
                        }
                        else {
                            statusMessage.value = Event("Error Occurred")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            statusMessage.value = Event("Error Occurred")
            e.printStackTrace()
        }
    }

    fun loginUser(user: User) {
        try {
            if (loginInputCheck()) {
                viewModelScope.launch(Dispatchers.IO) {
                    val email: String = currentUser.value!!.email
                    var foundUser = getUserByEmail(email)
                    withContext(Dispatchers.Main) {
                        if (foundUser != null) {
                            currentUser.value = foundUser!!
                            loginCompletion.value = true
                        }
                        else {
                            statusMessage.value = Event("Error Occurred")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            statusMessage.value = Event("Error Occurred")
            e.printStackTrace()
        }

    }

    private fun signInInputCheck(): Boolean {
        if (currentUser.value == null ||
            currentUser.value!!.firstName.isEmpty()
        ) {
            firstNameErrorMessage.value = appContext.getString(R.string.can_not_be_empty)
        } else {
            firstNameErrorMessage.value = ""
            if (currentUser.value!!.lastName.isEmpty()) {
                lastNameErrorMessage.value = appContext.getString(R.string.can_not_be_empty)
            } else {
                lastNameErrorMessage.value = ""
                if (currentUser.value!!.email.isEmpty() ||
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(currentUser.value!!.email)
                        .matches()
                ) {
                    emailErrorMessage.value = appContext.getString(R.string.email_is_not_valid)
                } else {
                    emailErrorMessage.value = ""
                    return true
                }
            }
        }
        return false
    }

    private fun loginInputCheck(): Boolean {
        if (currentUser.value == null ||
            currentUser.value!!.email.isEmpty() ||
            !android.util.Patterns.EMAIL_ADDRESS.matcher(currentUser.value!!.email).matches() ){
            emailErrorMessage.value = appContext.getString(R.string.email_is_not_valid)
        } else {
            emailErrorMessage.value = ""
            if (currentUser.value!!.password!!.length < 8) {
                passwordErrorMessage.value = appContext.getString(R.string.password_is_ot_valid)
            } else {
                passwordErrorMessage.value = ""
                return true
            }
        }
        return  false
    }

}
