package com.minamagid.mazaady.presentation.staticScreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.minamagid.mazaady.R
import com.minamagid.mazaady.presentation.staticScreen.adapter.CarSellAdapter
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
