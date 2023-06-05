package  com.minamagid.mazaady.presentation.home.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import android.widget.Filter
import com.minamagid.mazaady.databinding.DropDownItemBinding
import com.minamagid.mazaady.domain.model.options.Option
import java.util.*
import kotlin.collections.ArrayList


class OptionsAdapter(
    private val clickListener: ClickListener?,
    private val itemList: ArrayList<Option>
) :
    ListAdapter<Option, BaseViewHolder>(
        USER_COMPARATOR
    ), Filterable {
    private var filteredList: ArrayList<Option> = itemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder(
            DropDownItemBinding.inflate(
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = ArrayList<Option>()
                val query = constraint?.toString()?.toLowerCase(Locale.getDefault())

                for (item in itemList) {
                    if (item.slug?.toLowerCase(Locale.getDefault())?.contains(query.toString()) == true) {
                        filteredResults.add(item)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredResults
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<Option>
                notifyDataSetChanged()
            }
        }
    }
    override fun getItemCount(): Int {
        return filteredList.size
    }
    inner class ViewHolder(var binding: DropDownItemBinding) :
        BaseViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun onBind(position: Int) {
            val item = getItem(position)
            binding.run {
                txtSlug.text = item.slug?:""

                if (item.id == -1){
                    txtSlug.setOnClickListener {
                        clickListener?.onOtherClick(it,item,position)
                    }
                }else{
                    txtSlug.setOnClickListener {
                        clickListener?.onItemClick(it,item,position)
                    }
                }

            }
        }

    }


    companion object {
        private val USER_COMPARATOR =
            object : DiffUtil.ItemCallback<Option>() {
                override fun areItemsTheSame(
                    oldItem: Option,
                    newItem: Option
                ): Boolean =
                    newItem.id == oldItem.id

                override fun areContentsTheSame(
                    oldItem: Option,
                    newItem: Option
                ): Boolean =
                    newItem == oldItem
            }
    }

    interface ClickListener {
        fun onItemClick(
            v: View,
            model: Option,
            position: Int
        )
        fun onOtherClick(
            v: View,
            model: Option,
            position: Int
        )
    }
}


