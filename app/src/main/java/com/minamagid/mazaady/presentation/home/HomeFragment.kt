package com.minamagid.mazaady.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.minamagid.mazaady.MainActivity
import com.minamagid.mazaady.R
import com.minamagid.mazaady.domain.model.category.Category
import com.minamagid.mazaady.domain.model.category.Children
import com.minamagid.mazaady.domain.model.category.Data
import com.minamagid.mazaady.domain.model.general.LocalSelectedModel
import com.minamagid.mazaady.presentation.home.adapter.ProcessAdapter
import com.minamagid.mazaady.utlis.showBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    lateinit var viewDataBinding: View
    private var catStrings = ArrayList<String?>()
    private var subCatStrings = ArrayList<String?>()
    private var listOfProgressTypeStrings = ArrayList<String?>()
    private var listOfCats = ArrayList<Category?>()
    private var listOfSubCats = ArrayList<Children?>()
    private val myList: ArrayList<LocalSelectedModel> =ArrayList<LocalSelectedModel>()

    var listOfProgressType = ArrayList<Data?>()
    var listOfSubOptions = ArrayList<Data?>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        viewDataBinding = root

        myObserve()
        myListener()



        return root
    }


    private fun myListener() {
        viewDataBinding.categorySP.setOnClickListener {
            requireContext().showBottomSheetDialog(
                it,
                listOfCats,
                ArrayList(emptyList()),
                listOfProgressType,
                emptyList(),
                viewModel
                , 0, {}, ""
            )
        }
        viewDataBinding.subCategorySP.setOnClickListener {
            requireContext().showBottomSheetDialog(
                it,
                listOfCats,
                listOfSubCats,
                listOfProgressType,
                emptyList(),
                viewModel
                , 1, {}, ""
            )
        }

        viewDataBinding.submitBtn.setOnClickListener {
            val gson = Gson()
            val jsonString = gson.toJson(MainActivity.getAllGeneral())
            val sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("myListKey", jsonString)
            editor.apply()
            MainActivity.clearGeneral()

            it.findNavController().navigate(R.id.action_navigation_home_to_navigation_result)
        }

        viewDataBinding.goToStaticBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_home_to_navigation_static)
        }
    }

    private fun myObserve() {
        viewModel.catRemote.observe(viewLifecycleOwner) {
            listOfCats.clear()
            it?.categories?.forEach {
                catStrings.add(it.slug)
                listOfCats.add(it)
            }
        }
        viewModel.subCats.observe(viewLifecycleOwner) {
            listOfSubCats.clear()
            it?.forEach {
                subCatStrings.add(it?.slug)
                listOfSubCats.add(it)
            }
        }
        viewModel.backSelected.observe(viewLifecycleOwner) {
            categorySP.text = Editable.Factory.getInstance().newEditable(it)
        }
        viewModel.backSelectedSubCats.observe(viewLifecycleOwner) {
            subCategorySP.text = Editable.Factory.getInstance().newEditable(it?:"")
        }
        viewModel.backSelectedSubCatsId.observe(viewLifecycleOwner) {
            viewModel.getProcessType(it)
        }
        viewModel.processTypeRemote.observe(viewLifecycleOwner) {
            listOfProgressType.clear()
            it?.forEach {
                listOfProgressTypeStrings.add(it?.slug)
                listOfProgressType.add(it)
            }
            val processType = ProcessAdapter( object : ProcessAdapter.ClickListener{
                override fun onItemClick(v: View, model: Data, position: Int) {
                    requireContext().showBottomSheetDialog(v,listOfCats, listOfSubCats, listOfProgressType,model.options,viewModel, 2,{},model.slug?:"")
                }
            },viewModel,viewLifecycleOwner)
            processType.submitList(it)
            viewDataBinding.processRV.adapter = processType
        }

        viewModel.backSelectedOptionId.observe(viewLifecycleOwner) {
            viewModel.getSubOptions(it,0)
        }
        viewModel.backSelectedOptionId2.observe(viewLifecycleOwner) {
            viewModel.getSubOptions(it,1)
        }

    }

}
