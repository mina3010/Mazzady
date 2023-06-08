package com.minamagid.mazaady.utlis

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.minamagid.mazaady.MainActivity
import com.minamagid.mazaady.R
import com.minamagid.mazaady.databinding.DialogSpinnerBinding
import com.minamagid.mazaady.domain.model.category.Category
import com.minamagid.mazaady.domain.model.category.Children
import com.minamagid.mazaady.domain.model.category.Data
import com.minamagid.mazaady.domain.model.general.LocalSelectedModel
import com.minamagid.mazaady.domain.model.options.Option
import com.minamagid.mazaady.presentation.home.HomeViewModel
import com.minamagid.mazaady.presentation.home.adapter.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.process_item.view.*

fun Context.showBottomSheetDialog(
    vw: View,
    listOfCats: ArrayList<Category?>?,
    listOfSubCats: ArrayList<Children?>?,
    listOfOptions: List<Option>?,
    vm: HomeViewModel?,
    type: Int?,
    value: String,
    positionItem: Int,

    ) {
    val dialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
    dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    dialog.window?.setLayout(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT
    )
    val binding: DialogSpinnerBinding = DataBindingUtil.inflate(
        LayoutInflater.from(this), R.layout.dialog_spinner, null,
        false
    )
    dialog.setContentView(binding.root)
    dialog.setCancelable(true)
    val behavior = BottomSheetBehavior.from(binding.root.parent as View)
    behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()
    when (type) {
        0 -> {
            binding.more.isVisible = false
            binding.header.text = "Main Category"
            val adapter = CategoriesAdapter(object : CategoriesAdapter.ClickListener {
                override fun onItemClick(v: View, model: Category, position: Int) {
                    vm?.backSelectedSubCats?.value = ""
                    vm?.backSelectedSubCatsId?.value = 0
                    vm?.processTypeRemote?.value = ArrayList(emptyList())
                    vm?.isSelectedItem?.value = false
                    vm?.isPosition?.value = -1
                    vm?.backSelected?.value = model.slug ?: ""
                    vm?.subCats?.value = model.children
                    dialog.dismiss()
                    val item = LocalSelectedModel(0, "Main Category", model.slug ?: "")
                    val itemsToDelete = mutableListOf<LocalSelectedModel?>()

                    if (!MainActivity.getAllGeneral().isNullOrEmpty()) {
                        for (it in MainActivity.getAllGeneral()) {
                            if (item.name == it?.name) {
                                itemsToDelete.add(it)
                            }
                        }
                    }

                    MainActivity.getAllGeneral().removeAll(itemsToDelete)
                    MainActivity.addGeneral(item)

                }
            },ArrayList(listOfCats))
            adapter.submitList(listOfCats)
            binding.dialogRv.adapter = adapter

            binding.searchDialog.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    adapter.filter.filter(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
        1 -> {
            binding.more.isVisible = false
            binding.header.text = "Sub Category"
            val adapterSub = SubCategoriesAdapter(object : SubCategoriesAdapter.ClickListener {
                override fun onItemClick(v: View, model: Children, position: Int) {
                    vm?.backSelectedSubCats?.value = model.slug
                    vm?.backSelectedSubCatsId?.value = model.id
                    vm?.isSelectedItem?.value = false
                    vm?.isPosition?.value = -1
                    dialog.dismiss()
                    val item = LocalSelectedModel(0, "Sub Category", model.slug ?: "")

                    val itemsToDelete = mutableListOf<LocalSelectedModel?>()

                    if (!MainActivity.getAllGeneral().isNullOrEmpty()) {
                        for (it in MainActivity.getAllGeneral()) {
                            if (item.name == it?.name) {
                                itemsToDelete.add(it)
                            }
                        }
                    }

                    MainActivity.getAllGeneral().removeAll(itemsToDelete)
                    MainActivity.addGeneral(item)

                }
            }, ArrayList(listOfSubCats))
            adapterSub.submitList(listOfSubCats)
            binding.dialogRv.adapter = adapterSub

            binding.searchDialog.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    adapterSub.filter.filter(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })

        }
        2 -> {
            binding.header.text = value
            val processType = OptionsAdapter(object : OptionsAdapter.ClickListener {
                override fun onItemClick(v: View, model: Option, position: Int) {
                    dialog.dismiss()
                    vw.processTypeItem.text = Editable.Factory.getInstance().newEditable(
                        if (!model.slug.isNullOrEmpty()) model.slug ?: "" else binding.more.text.toString()
                    )

                    vm?.isSelectedItem?.value = false
                    vm?.isPosition?.value = -1
                    val item = LocalSelectedModel(0, value, model.slug ?: "")

                    val itemsToDelete = mutableListOf<LocalSelectedModel?>()

                    if (!MainActivity.getAllGeneral().isNullOrEmpty()) {
                        for (it in MainActivity.getAllGeneral()) {
                            if (item.name == it?.name) {
                                itemsToDelete.add(it)
                            }
                        }
                    }

                    MainActivity.getAllGeneral().removeAll(itemsToDelete.toSet())
                    MainActivity.addGeneral(item)
                    if (model.child==true) {
                        vm?.backSelectedOptionId?.value = model.id ?: 0
                        vm?.backSelectedOptionTxt?.value = model.slug ?: ""
                    }
                }

                override fun onOtherClick(v: View, model: Option, position: Int) {
                    vw.processTypeItem.text = Editable.Factory.getInstance().newEditable("Other")
                    dialog.dismiss()
                    vm?.isSelectedItem?.value = true
                    vm?.isPosition?.value = positionItem
                    vm?.backSelectedOptionId?.value = -1
                }
            }, ArrayList(listOfOptions))
            processType.submitList(listOfOptions)
            binding.dialogRv.adapter = processType

            binding.searchDialog.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    processType.filter.filter(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
        3 -> {
            binding.header.text = value
            val processType = OptionsAdapter(object : OptionsAdapter.ClickListener {
                override fun onItemClick(v: View, model: Option, position: Int) {
                    dialog.dismiss()
                    vm?.isSelectedItem?.value = false
                    vm?.isPosition?.value = -1

                    vw.processTypeItem.text = Editable.Factory.getInstance().newEditable(
                        if (!model.slug.isNullOrEmpty()) model.slug ?: "" else binding.more.text.toString()
                    )
                    val item = LocalSelectedModel(0, value, model.slug ?: "")

                    val itemsToDelete = mutableListOf<LocalSelectedModel?>()

                    if (!MainActivity.getAllGeneral().isNullOrEmpty()) {
                        for (it in MainActivity.getAllGeneral()) {
                            if (item.name == it?.name) {
                                itemsToDelete.add(it)
                            }
                        }
                    }

                    MainActivity.getAllGeneral().removeAll(itemsToDelete.toSet())
                    MainActivity.addGeneral(item)

                    if (model.child==true) {
                        vm?.backSelectedOptionId2?.value = model.id ?: 0
                        vm?.backSelectedOptionTxt?.value = model.slug ?: ""
                    }
                }

                override fun onOtherClick(v: View, model: Option, position: Int) {
                    vw.processTypeItem.text = Editable.Factory.getInstance().newEditable("Other")
                    model.isSelected = true
                    dialog.dismiss()
                    vm?.isSelectedItem?.value = true
                    vm?.isPosition?.value = positionItem
                }
            }, ArrayList(listOfOptions))
            processType.submitList(listOfOptions)
            binding.dialogRv.adapter = processType


            binding.searchDialog.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    processType.filter.filter(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

    }

    dialog.setOnDismissListener { dialog.dismiss() }
    binding.run {
        closeDialog.setOnClickListener { dialog.dismiss() }
    }
}


