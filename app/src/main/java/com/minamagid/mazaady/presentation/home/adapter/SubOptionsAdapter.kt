package  com.minamagid.mazaady.presentation.home.adapter

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.minamagid.mazaady.databinding.ProcessItemBinding
import com.minamagid.mazaady.domain.model.category.Data
import com.minamagid.mazaady.presentation.home.HomeViewModel


class SubOptionsAdapter(
    private val clickListener: ClickListener?,
    val vm: HomeViewModel,
    val viewLifecycleOwner: LifecycleOwner,
) :
    ListAdapter<Data, BaseViewHolder>(
        USER_COMPARATOR
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder(
            ProcessItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }


    inner class ViewHolder(var binding: ProcessItemBinding) :
        BaseViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun onBind(position: Int) {
            val item = getItem(position)
            binding.run {
                index = position
                sp.hint = item.slug?:""
                processTypeItem.setOnClickListener {
                    clickListener?.onItemClick(it,item,position)
                }


                vm.isSelectedItem.observe(viewLifecycleOwner){
                    isSelect = it
                }
                vm.isPosition.observe(viewLifecycleOwner){
                    isPosition = it
                    if (isSelect == true && index == isPosition){
                        binding.otherEd.isVisible = true
                        binding.otherEdParent.isVisible = true
                    }
                }

                binding.otherEd.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        clickListener?.onSpecifyData(item,s.toString())
                    }
                })
                binding.subProcessRV.isVisible = true
            }
        }


    }


    companion object {
        private val USER_COMPARATOR =
            object : DiffUtil.ItemCallback<Data>() {
                override fun areItemsTheSame(
                    oldItem: Data,
                    newItem: Data
                ): Boolean =
                    newItem.id == oldItem.id

                override fun areContentsTheSame(
                    oldItem: Data,
                    newItem: Data
                ): Boolean =
                    newItem == oldItem
            }
    }

    interface ClickListener {
        fun onItemClick(
            v: View,
            model: Data,
            position: Int
        )
        fun onSpecifyData(
            model: Data,
            txt: String
        )
    }

}


