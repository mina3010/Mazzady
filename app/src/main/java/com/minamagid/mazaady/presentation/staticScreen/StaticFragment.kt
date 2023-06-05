package com.minamagid.mazaady.presentation.staticScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.minamagid.mazaady.R
import com.minamagid.mazaady.presentation.staticScreen.adapter.CarSellAdapter
import com.minamagid.mazaady.presentation.staticScreen.adapter.ImageSliderAdapter
import kotlinx.android.synthetic.main.fragment_static.view.*

class StaticFragment : Fragment() {
    lateinit var viewDataBinding: View
    private lateinit var mAdapter: ImageSliderAdapter
    private val list = listOf(5,6,4,64,4)
    private val mImageResources = intArrayOf(
        R.drawable.car,
        R.drawable.car_img,
        R.drawable.car,
    )
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
        val adapter = CarSellAdapter()
        adapter.submitList(list)
        viewDataBinding.productsRv.adapter = adapter

        mAdapter = ImageSliderAdapter(requireContext(), mImageResources)
        viewDataBinding.mViewPager.adapter = mAdapter
        viewDataBinding.indicator.createIndicators(5,0)
        viewDataBinding.indicator.setViewPager(viewDataBinding.mViewPager)
//        viewDataBinding.indicator.animatePageSelected(2)
    }

}
