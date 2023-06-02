package  com.minamagid.mazaady.presentation.home.adapter

import android.os.Build
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
import com.minamagid.mazaady.utlis.showBottomSheetDialog


class ProcessAdapter(
    private val clickListener: ClickListener?,
    private val vm: HomeViewModel,
    private val viewLifecycleOwner: LifecycleOwner,
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
                sp.hint = item.slug?:""
                processTypeItem.setOnClickListener {
                    clickListener?.onItemClick(it,item,position)
                }
//                binding.subProcessRV.isVisible = true
//                binding.subProcessRV2.isVisible = true

                if (item.options?.firstOrNull()?.child==true) {
                    vm.subOptions.observe(viewLifecycleOwner) {
                        val adapter = SubOptionsAdapter( object : SubOptionsAdapter.ClickListener{
                            override fun onItemClick(v: View, model: Data, position: Int) {
                                itemView.context.showBottomSheetDialog(v,
                                    null, null, null,model.options,vm, 3,{},model.slug?:"")
                            }
                        })
                        adapter.submitList(it)
                        binding.subProcessRV.adapter = adapter

                    }
                    vm.subOptions2.observe(viewLifecycleOwner) {
                        val adapter = SubOptionsAdapter( object : SubOptionsAdapter.ClickListener{
                            override fun onItemClick(v: View, model: Data, position: Int) {
                                itemView.context.showBottomSheetDialog(v,
                                    null, null, null,model.options,vm, 3,{},model.slug?:"")
                            }
                        })
                        adapter.submitList(it)
                        binding.subProcessRV2.adapter = adapter

                    }

                }

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
    }
}


