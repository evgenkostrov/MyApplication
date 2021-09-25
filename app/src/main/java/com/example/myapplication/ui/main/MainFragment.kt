package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.myapplication.Person
import com.example.myapplication.R
import com.example.myapplication.databinding.MainActivityBinding
import com.example.myapplication.databinding.MainFragmentBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : Fragment() {

    companion object {
        fun newInstance(personId:Long):MainFragment {
           val fragment = MainFragment()
            val args = Bundle()
            args.putLong("KEY", personId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.loadPerson(arguments?.getLong("KEY")?:42L)
        viewModel.person.onEach { person -> if(person!=null) displayPerson(person) }.launchIn(lifecycleScope)

binding.close.setOnClickListener { requireActivity().supportFragmentManager.beginTransaction()
    .remove(this)
    .commitNow()}
    }

    private fun displayPerson(person: Person){
        binding.message.text=person.name+person.status+person.gender.ordinal.toString()+person.id.toString()
        binding.imageView.load(person.image)
    }
}