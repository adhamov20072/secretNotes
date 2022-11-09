package com.aimardon.secretnotes.fragments

import android.app.ProgressDialog.show
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.aimardon.secretnotes.R
import com.aimardon.secretnotes.adapters.RecyclerViewAdapter
import com.aimardon.secretnotes.dataBase.DataBase
import com.aimardon.secretnotes.databinding.FragmentHomeBinding
import com.aimardon.secretnotes.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {
    lateinit var binding: FragmentHomeBinding
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewAdapter = RecyclerViewAdapter()
        GlobalScope.launch(IO) {
            setList()
        }
        binding.recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.setClickListener(object : RecyclerViewAdapter.SetOnLongClickListener {
            override fun longClick(note: Note) {
                GlobalScope.launch(IO) {
                    DataBase.DataBaseBuilder.getDataBase(requireContext())?.noteDao()?.delete(note)
                    setList()
                }
            }

            override fun setOnClickListener(note: Note) {
                val action = HomeFragmentDirections.actionHomeFragmentToAddFragment(note)
                findNavController().navigate(action)
            }

        })
        binding.btnadd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
        binding.edSearch.setOnQueryTextListener(this)

    }

    fun setList() {
        val list = DataBase.DataBaseBuilder.getDataBase(requireContext())?.noteDao()?.getAllNotes()
        recyclerViewAdapter.submitList(list)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchedTitle(newText.toString())
        return true
    }

    fun searchedTitle(text: String) {
        val filteredList = ArrayList<Note>()
        GlobalScope.launch(IO) {
            for (item in DataBase.DataBaseBuilder.getDataBase(requireContext())?.noteDao()
                ?.getAllNotes()!!) {
                if (item.title.lowercase().contains(text.lowercase())) {
                    filteredList.add(item)
                }
            }
            if (filteredList.isNotEmpty()) {
                recyclerViewAdapter.submitList(filteredList)
            }
        }
    }
}