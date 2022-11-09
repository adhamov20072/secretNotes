package com.aimardon.secretnotes.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aimardon.secretnotes.R
import com.aimardon.secretnotes.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences =
            requireContext().getSharedPreferences("PreferencesBase", Context.MODE_PRIVATE)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            if (sharedPreferences.getString(
                    "login",
                    null
                ) == binding.editText1.text.toString() && sharedPreferences.getString(
                    "parol",
                    null
                ) == binding.editText2.text.toString()
            ) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }
}
