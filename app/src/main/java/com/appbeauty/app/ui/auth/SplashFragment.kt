package com.appbeauty.app.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.appbeauty.app.R
import com.appbeauty.app.data.repository.AuthRepository
import com.appbeauty.app.databinding.FragmentSplashBinding
import kotlinx.coroutines.*

class SplashFragment : Fragment() {
    val parent: AuthActivity by lazy { activity as AuthActivity }
    val viewModel:AuthViewModel by lazy { AuthViewModel(AuthRepository(parent)) }
    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            withContext(Dispatchers.Main){
                if (viewModel.isLogin.value == true){
                    parent.onSuccess(viewModel.authUser.value)
                } else {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                }
            }
        }
    }

}