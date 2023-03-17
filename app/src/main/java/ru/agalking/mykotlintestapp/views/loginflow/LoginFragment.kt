package ru.agalking.mykotlintestapp.views.loginflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.agalking.mykotlintestapp.R

class LoginFragment : Fragment() {

   private lateinit var viewModel: LoginViewModel

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)

       viewModel = ViewModelProvider(this)[LoginViewModel::class.java] // query for a ProfileViewModel
   }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

}