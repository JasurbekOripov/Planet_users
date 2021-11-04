package uz.juo.planetusers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.juo.planetusers.databinding.HomeRvItemBinding
import uz.juo.planetusers.models.Data

class HomeRvAdapter(var setOnClick: itemClick) :
    PagingDataAdapter<Data, HomeRvAdapter.Vh>(MyDiffUtill()) {
    inner class Vh(var item: HomeRvItemBinding) : RecyclerView.ViewHolder(item.root) {
        fun onBind(data: Data, position: Int) {
            setOnClick.dataListener(data, position)
//            Picasso.get().load(data.avatar).into(item.itemImg)
//            item.apply {
//                firstName.text = data.first_name
//                lastName.text = data.last_name
//                email.text = data.email
//            }
//            item.root.setOnClickListener {
//                setOnClick.setOnClick(data, position)
//            }
        }
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position) ?: Data(), position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(HomeRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class MyDiffUtill : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    interface itemClick {
        fun setOnClick(data: Data, position: Int)
        fun setOnEditClick(data: Data, position: Int)
        fun setOnDeleteClick(data: Data, position: Int)
        fun dataListener(data: Data, position: Int)
    }
}