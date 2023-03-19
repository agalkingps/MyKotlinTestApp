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
        return FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
        }.root
    }

}