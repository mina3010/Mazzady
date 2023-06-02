package com.minamagid.mazaady.presentation.staticScreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.minamagid.mazaady.R
import com.minamagid.mazaady.domain.model.category.Data
import com.minamagid.mazaady.domain.model.general.LocalSelectedModel
import com.minamagid.mazaady.presentation.home.adapter.ProcessAdapter
import com.minamagid.mazaady.presentation.resultScreen.adapter.ResultAdapter
import com.minamagid.mazaady.presentation.staticScreen.adapter.CarSellAdapter
import com.minamagid.mazaady.utlis.showBottomSheetDialog
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_result.view.*
import kotlinx.android.synthetic.main.fragment_static.view.*

class StaticFragment : Fragment() {
    lateinit var viewDataBinding: View

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_static, container, false)
        viewDataBinding = root

        myAdapter()

        return root
    }

    private fun myAdapter() {
        val list = listOf<Int>(5,6,4,64,4)
        val adapter = CarSellAdapter()
        adapter.submitList(list)
        viewDataBinding.productsRv.adapter = adapter
    }

}
