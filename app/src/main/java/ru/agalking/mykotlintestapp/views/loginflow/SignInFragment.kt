package ru.agalking.mykotlintestapp.views.loginflow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import ru.agalking.mykotlintestapp.R
import ru.agalking.mykotlintestapp.data.users.local.entities.User
import ru.agalking.mykotlintestapp.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private val sharedViewModel: LoginFlowViewModel by activityViewModels()
//    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewModel = ViewModelProvider(this)[LoginViewModel::class.java] // query for a ProfileViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewBinding = FragmentSignInBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
        }
        viewBinding.signInButton.setOnClickListener {
            if (inputCheck()) {
                sharedViewModel.signInNewUser(sharedViewModel.currentUser.value!!)
//                (activity as NavigationHost).navigateTo(ProductGridFragment(), false) // Navigate to the next Fragment
            }
        }
        return viewBinding.root
    }

    private fun inputCheck(): Boolean {
        if (sharedViewModel.currentUser.value == null ||
            sharedViewModel.currentUser.value!!.firstName.isEmpty()
        ) {
            sharedViewModel.firstNameErrorMessage.value = getString(R.string.can_not_be_empty)
        } else {
            sharedViewModel.firstNameErrorMessage.value = ""
            if (sharedViewModel.currentUser.value!!.lastName.isEmpty()) {
                sharedViewModel.lastNameErrorMessage.value = getString(R.string.can_not_be_empty)
            } else {
                sharedViewModel.lastNameErrorMessage.value = ""
                if (sharedViewModel.currentUser.value!!.email.isEmpty() ||
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(sharedViewModel.currentUser.value!!.email)
                        .matches()
                ) {
                    sharedViewModel.emailErrorMessage.value = getString(R.string.email_is_not_valid)
                } else {
                    sharedViewModel.emailErrorMessage.value = ""
                    return true
                }
            }
        }
        return false
    }
}