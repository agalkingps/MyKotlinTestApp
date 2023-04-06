package ru.agalking.mykotlintestapp.views.loginflow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ru.agalking.mykotlintestapp.R
import ru.agalking.mykotlintestapp.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private val sharedViewModel: LoginFlowViewModel by activityViewModels()

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
            sharedViewModel.signInNewUser(sharedViewModel.currentUser.value!!)
        }

        viewBinding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
        }

        sharedViewModel.signInCompletion.observe(viewLifecycleOwner, Observer {
           if (it) {
               findNavController().navigate(R.id.action_signInFragment_to_userListFragment)
               sharedViewModel.signInCompletion.value = false
           }
        })

        sharedViewModel.statusMessage.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        })

       return viewBinding.root
    }

}