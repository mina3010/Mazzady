package com.minamagid.mazaady.presentation.resultScreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.minamagid.mazaady.R
import com.minamagid.mazaady.domain.model.general.LocalSelectedModel
import com.minamagid.mazaady.presentation.resultScreen.adapter.ResultAdapter
import kotlinx.android.synthetic.main.fragment_result.view.*

class ResultFragment : Fragment() {
    lateinit var viewDataBinding: View

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_result, container, false)
        viewDataBinding = root

        myAdapter()

        viewDataBinding.goToStaticBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_result_to_navigation_static)

        }

        return root
    }

    private fun myAdapter() {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("myListKey", null)

        val gson = Gson()
        val myList: List<LocalSelectedModel> = gson.fromJson(jsonString, object : TypeToken<List<LocalSelectedModel>>() {}.type)

        val resAdapter = ResultAdapter()
        resAdapter.submitList(myList)
        viewDataBinding.resultRV.adapter = resAdapter
    }

}
