package ru.agalking.mykotlintestapp.views.loginflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import ru.agalking.mykotlintestapp.R
import ru.agalking.mykotlintestapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    // ViewModel shared between the activity and its fragments
    private val sharedViewModel: LoginViewModel by activityViewModels()

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
/*
        viewBinding.loginButton.setOnClickListener {
            if (viewBinding.editTextEmail.text!!.isEmpty() ||
                !android.util.Patterns.EMAIL_ADDRESS.matcher(viewBinding.editTextEmail.text).matches() ){
                sharedViewModel.emailErrorMessage.value = getString(R.string.email_is_not_valid)
            } else {
                sharedViewModel.emailErrorMessage.value = ""
                if (viewBinding.editTextPassword.text != null &&
                    viewBinding.editTextPassword.text!!.length < 8) {
                    sharedViewModel.passwordErrorMessage.value = getString(R.string.password_is_ot_valid)
                } else {
                    sharedViewModel.passwordErrorMessage.value = ""
                }
            }
        }
*/
        viewBinding.loginButton.setOnClickListener {
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
                }
            }
        }


        return viewBinding.root
    }

}