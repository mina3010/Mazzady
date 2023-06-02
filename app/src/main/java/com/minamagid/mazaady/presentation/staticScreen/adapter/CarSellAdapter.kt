package  com.minamagid.mazaady.presentation.staticScreen.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.minamagid.mazaady.databinding.CarItemBinding
import com.minamagid.mazaady.presentation.home.adapter.BaseViewHolder


class CarSellAdapter():
    ListAdapter<Int, BaseViewHolder>(
        USER_COMPARATOR
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder(
            CarItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }


    override fun getItemCount(): Int {
        return 5
    }
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    inner class ViewHolder(var binding: CarItemBinding) :
        BaseViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun onBind(position: Int) {
            val item = getItem(position)

        }


    }


    companion object {
        private val USER_COMPARATOR =
            object : DiffUtil.ItemCallback<Int>() {
                override fun areItemsTheSame(
                    oldItem: Int,
                    newItem: Int
                ): Boolean =
                    newItem == oldItem

                override fun areContentsTheSame(
                    oldItem: Int,
                    newItem: Int
                ): Boolean =
                    newItem == oldItem
            }
    }

}


