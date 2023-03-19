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
import ru.agalking.mykotlintestapp.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private val sharedViewModel: LoginViewModel by activityViewModels()
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
            if (viewBinding.editTextFirstName.text!!.isEmpty()) {
                sharedViewModel.firstNameErrorMessage.value = getString(R.string.can_not_be_empty)
            } else {
                sharedViewModel.firstNameErrorMessage.value = ""
                if (viewBinding.editTextLastName.text!!.isEmpty()) {
                    sharedViewModel.lastNameErrorMessage.value = getString(R.string.can_not_be_empty)
                } else {
                    sharedViewModel.lastNameErrorMessage.value = ""
                    if (viewBinding.editTextEmail.text!!.isEmpty() ||
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(viewBinding.editTextEmail.text).matches() ){
                        sharedViewModel.emailErrorMessage.value = getString(R.string.email_is_not_valid)
                    } else {
                        sharedViewModel.emailErrorMessage.value = ""
//                (activity as NavigationHost).navigateTo(ProductGridFragment(), false) // Navigate to the next Fragment
                    }
                }
            }
        }
        return viewBinding.root
    }
}