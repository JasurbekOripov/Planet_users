package uz.juo.planetusers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.juo.planetusers.R
import uz.juo.planetusers.databinding.HomeRvItemBinding
import uz.juo.planetusers.room.entity.UserEntity

class UserAdapter( var context:Context,var list:ArrayList<UserEntity>,var listen: itemClick1) :
RecyclerView.Adapter<UserAdapter.UserVh>(){

    inner class UserVh(var item: HomeRvItemBinding) : RecyclerView.ViewHolder(item.root) {
        fun onBind(user: UserEntity, position: Int) {
            Picasso.get().load(user.avatar).into(item.itemImg)
            var anim = AnimationUtils.loadAnimation(context, R.anim.rv)
            item.root.startAnimation(anim)
            item.apply {
                firstName.text = user.first_name
                lastName.text = user.last_name
                email.text = user.email
                edit.setOnClickListener {
                    listen.setOnEditClick1(user, position)
                }
                l1.setOnClickListener {
                    listen.setOnClick1(user, position)
                }
                delete.setOnClickListener {
                    listen.setOnDeleteClick1(user, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVh {
        return UserVh(HomeRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserVh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int = list.size

    interface itemClick1 {
        fun setOnClick1(data: UserEntity, position: Int)
        fun setOnEditClick1(data: UserEntity, position: Int)
        fun setOnDeleteClick1(data: UserEntity, position: Int)
    }
}