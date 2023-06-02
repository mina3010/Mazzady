package  com.minamagid.mazaady.presentation.home.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.minamagid.mazaady.databinding.DropDownItemBinding
import com.minamagid.mazaady.domain.model.category.Category
import com.minamagid.mazaady.domain.model.category.Children
import java.util.*
import kotlin.collections.ArrayList

class CategoriesAdapter(
    private val clickListener: ClickListener,
    private val itemList: ArrayList<Category?>
) :
    ListAdapter<Category, BaseViewHolder>(
        USER_COMPARATOR
    ) , Filterable {
    private var filteredList: ArrayList<Category?> = itemList
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
                val filteredResults = ArrayList<Category>()
                val query = constraint?.toString()?.toLowerCase(Locale.getDefault())

                Log.d("mina_search","1")
                for (item in itemList) {
                    Log.d("mina_search","2")
                    Log.d("mina_search","$query, ${item?.slug?.toLowerCase(Locale.getDefault())?.contains(query?:"")}")

                    if (item?.slug?.toLowerCase(Locale.getDefault())?.contains(query?:"")==true) {
                        Log.d("mina_search","3")

                        filteredResults.add(item)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredResults
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<Category?>
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
                    clickListener.onItemClick(it,item,position)
                }
            }
        }

    }


    companion object {
        private val USER_COMPARATOR =
            object : DiffUtil.ItemCallback<Category>() {
                override fun areItemsTheSame(
                    oldItem: Category,
                    newItem: Category
                ): Boolean =
                    newItem.id == oldItem.id

                override fun areContentsTheSame(
                    oldItem: Category,
                    newItem: Category
                ): Boolean =
                    newItem == oldItem
            }
    }

    interface ClickListener {
        fun onItemClick(
            v: View,
            model: Category,
            position: Int
        )
    }
}



