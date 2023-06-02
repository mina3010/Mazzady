package  com.minamagid.mazaady.presentation.home.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minamagid.mazaady.databinding.DropDownItemBinding
import com.minamagid.mazaady.domain.model.category.Category
import com.minamagid.mazaady.domain.model.category.Children
import com.minamagid.mazaady.domain.model.category.Data
import com.minamagid.mazaady.domain.model.options.Option
import java.util.*
import kotlin.collections.ArrayList


class SubCategoriesAdapter(
    private val clickListener: ClickListener?,
    private val itemList: ArrayList<Children?>
) :
    ListAdapter<Children, BaseViewHolder>(
        USER_COMPARATOR
    ) , Filterable {
    private var filteredList: ArrayList<Children?> = itemList
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
                val filteredResults = ArrayList<Children>()

                val query = constraint?.toString()?.trim()?.toLowerCase(Locale.getDefault())

                for (item in itemList) {
                    val slug = item?.slug?.toLowerCase(Locale.getDefault())
                    if (slug?.contains(query?:"") == true || slug?.startsWith(query?:"") == true) {
                        filteredResults.add(item)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredResults
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<Children?>
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
                txtSlug.setOnClickListener {
                    clickListener?.onItemClick(it,item,position)
                }
            }
        }
    }


    companion object {
        private val USER_COMPARATOR =
            object : DiffUtil.ItemCallback<Children>() {
                override fun areItemsTheSame(
                    oldItem: Children,
                    newItem: Children
                ): Boolean =
                    newItem.id == oldItem.id

                override fun areContentsTheSame(
                    oldItem: Children,
                    newItem: Children
                ): Boolean =
                    newItem == oldItem
            }
    }

    interface ClickListener {
        fun onItemClick(
            v: View,
            model: Children,
            position: Int
        )
    }
}


