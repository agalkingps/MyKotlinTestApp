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
import ru.agalking.mykotlintestapp.R
import ru.agalking.mykotlintestapp.data.users.local.entities.User
import ru.agalking.mykotlintestapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    // ViewModel shared between the activity and its fragments
    private val sharedViewModel: LoginFlowViewModel by activityViewModels()

//    private lateinit var viewModel: LoginViewModel

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)

         // ViewModel specific for th Activity
//       viewModel = ViewModelProvider(this)[LoginViewModel::class.java] // query for a ProfileViewModel
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
                sharedViewModel.currentUser.value = User()
                sharedViewModel.logInNewUser(sharedViewModel.currentUser.value!!)
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
