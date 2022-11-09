package com.aimardon.secretnotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aimardon.secretnotes.R
import com.aimardon.secretnotes.dataBase.DataBase
import com.aimardon.secretnotes.databinding.FragmentAddBinding
import com.aimardon.secretnotes.model.Note
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddFragment : Fragment() {
    lateinit var binding: FragmentAddBinding
    val args: AddFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.notes != null) {
            binding.addtitle.setText(args.notes!!.title)
            binding.adddescription.setText(args.notes!!.description)
        }
        binding.materialButton.setOnClickListener {
            if (args.notes != null) {
                val note: Note = Note(
                    args.notes!!.id,
                    binding.addtitle.text.toString(),
                    binding.adddescription.text.toString()
                )
                GlobalScope.launch(IO) {
                    DataBase.DataBaseBuilder.getDataBase(requireContext())?.noteDao()?.update(note)
                }
            } else {
                val note: Note = Note(
                    0,
                    binding.addtitle.text.toString(),
                    binding.adddescription.text.toString()
                )
                if (binding.addtitle.text.isNotEmpty()) {
                    GlobalScope.launch(IO) {
                        DataBase.DataBaseBuilder.getDataBase(requireContext())?.noteDao()
                            ?.insert(note)
                    }
                }
            }
            if (!binding.addtitle.text.isEmpty()) {
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
            }
        }
    }
}