package ru.agalking.mykotlintestapp.views.loginflow

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.agalking.mykotlintestapp.R
import ru.agalking.mykotlintestapp.data.users.local.entities.User
import ru.agalking.mykotlintestapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    // ViewModel shared between the activity and its fragments
    private val sharedViewModel: LoginFlowViewModel by activityViewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
   }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewBinding =  FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
        }
        viewBinding.loginButton.setOnClickListener {
            if (inputCheck()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val email: String = sharedViewModel.currentUser.value!!.email
                    var foundUser = sharedViewModel.getUserByEmail(email)
                    if (foundUser != null) {
                        withContext(Dispatchers.Main) {
                            sharedViewModel.currentUser.value = foundUser!!
                            findNavController().navigate(R.id.action_loginFragment_to_userListFragment)
                        }
                    }
                }
            }
        }
        return viewBinding.root
    }

    private fun inputCheck(): Boolean {
        if (sharedViewModel.currentUser.value == null ||
            sharedViewModel.currentUser.value!!.email.isEmpty() ||
            !android.util.Patterns.EMAIL_ADDRESS.matcher(sharedViewModel.currentUser.value!!.email).matches() ){
            sharedViewModel.emailErrorMessage.value = getString(R.string.email_is_not_valid)
        } else {
            sharedViewModel.emailErrorMessage.value = ""
            if (sharedViewModel.currentUser.value!!.password!!.length < 8) {
                sharedViewModel.passwordErrorMessage.value = getString(R.string.password_is_ot_valid)
            } else {
                sharedViewModel.passwordErrorMessage.value = ""
                return true
            }
        }
        return  false
    }
}
