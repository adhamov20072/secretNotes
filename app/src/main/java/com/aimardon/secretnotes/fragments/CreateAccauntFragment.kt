package com.aimardon.secretnotes.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aimardon.secretnotes.R
import com.aimardon.secretnotes.databinding.FragmentCreateAccauntBinding


class CreateAccauntFragment : Fragment() {
    lateinit var binding: FragmentCreateAccauntBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = requireContext().getSharedPreferences("PreferencesBase", MODE_PRIVATE)
        if (sharedPreferences.getString("parol",null) !=null &&sharedPreferences.getString("parol",null)!=null){
            findNavController().navigate(R.id.action_createAccauntFragment_to_loginFragment)
        }
        binding = FragmentCreateAccauntBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            if (binding.editText1.text.isNotEmpty() && binding.editText2.text.isNotEmpty()) {
                sharedPreferences.edit().putString("login", "${binding.editText1.text}").apply()
                sharedPreferences.edit().putString("parol", "${binding.editText2.text}").apply()
                findNavController().navigate(R.id.action_createAccauntFragment_to_loginFragment)
            }
        }
    }
}