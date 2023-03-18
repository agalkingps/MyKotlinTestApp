package ru.agalking.mykotlintestapp.views.loginflow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
        return FragmentSignInBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            currentUser = sharedViewModel.getCurrentUser().value   // Attach your view model here
        }.root
    }

}