package  com.minamagid.mazaady.presentation.resultScreen.adapter

import android.os.Build
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minamagid.mazaady.databinding.DropDownItemBinding
import com.minamagid.mazaady.databinding.ResultItemBinding
import com.minamagid.mazaady.domain.model.category.Category
import com.minamagid.mazaady.domain.model.category.Children
import com.minamagid.mazaady.domain.model.category.Data
import com.minamagid.mazaady.domain.model.general.LocalSelectedModel
import com.minamagid.mazaady.domain.model.options.Option
import com.minamagid.mazaady.presentation.home.adapter.BaseViewHolder


class ResultAdapter(
) :
    ListAdapter<LocalSelectedModel, BaseViewHolder>(
        USER_COMPARATOR
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder(
            ResultItemBinding.inflate(
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

    inner class ViewHolder(var binding: ResultItemBinding) :
        BaseViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun onBind(position: Int) {
            val item = getItem(position)
            binding.run {
                txtKey.text = item.name?:""
                txtvalue.text = item.value?:""

            }
        }

    }


    companion object {
        private val USER_COMPARATOR =
            object : DiffUtil.ItemCallback<LocalSelectedModel>() {
                override fun areItemsTheSame(
                    oldItem: LocalSelectedModel,
                    newItem: LocalSelectedModel
                ): Boolean =
                    newItem.id == oldItem.id

                override fun areContentsTheSame(
                    oldItem: LocalSelectedModel,
                    newItem: LocalSelectedModel
                ): Boolean =
                    newItem == oldItem
            }
    }

}


