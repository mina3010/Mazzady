package com.minamagid.mazaady.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
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
import com.minamagid.mazaady.domain.model.options.Option
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
    var listOfProgressType = ArrayList<Data?>()
    val TAG="mina_home"

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
                emptyList(),
                viewModel
                , 0, "", -1
            )
        }
        viewDataBinding.subCategorySP.setOnClickListener {
            requireContext().showBottomSheetDialog(
                it,
                listOfCats,
                listOfSubCats,
                emptyList(),
                viewModel
                , 1,  "", -1
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

            viewModel.isPosition.value = -1
            viewModel.isSelectedItem.value = false
            it.findNavController().navigate(R.id.action_navigation_home_to_navigation_result)
        }

        viewDataBinding.goToStaticBtn.setOnClickListener {
            viewModel.isPosition.value = -1
            viewModel.isSelectedItem.value = false
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
                    val list = ArrayList<Option>()
                    list.add(Option(null,-1,"Other",null,"Other",false))
                    list.addAll(ArrayList(model.options?: emptyList()))
                    requireContext().showBottomSheetDialog(v,listOfCats, listOfSubCats,list,viewModel, 2,model.slug?:"",position)
                }

                override fun onSpecifyData( model: Data,txt: String) {
                    Log.d(TAG,"$txt||$model")
                    val item = LocalSelectedModel(0, model.slug?:"",txt)
                    val itemsToDelete = mutableListOf<LocalSelectedModel?>()

                    if (MainActivity.getAllGeneral().isNotEmpty()) {
                        for (it in MainActivity.getAllGeneral()) {
                            if (item.name == it?.name) {
                                itemsToDelete.add(it)
                            }
                        }
                    }

                    MainActivity.getAllGeneral().removeAll(itemsToDelete)
                    MainActivity.addGeneral(item)
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
