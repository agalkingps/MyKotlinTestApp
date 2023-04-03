package ru.agalking.mykotlintestapp.views.loginflow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.agalking.mykotlintestapp.data.users.local.entities.User
import ru.agalking.mykotlintestapp.databinding.UserDetailRowBinding

class UserListAdapter(
    private val onItemClicked: ((User) -> Unit)
) : ListAdapter<User, UserListAdapter.UserViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val viewHolder = UserViewHolder(
            UserDetailRowBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(
        private var binding: UserDetailRowBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.textViewId.text = user.id.toString()
            binding.textViewName.text = user.firstName + " " + user.lastName
            binding.textViewEmail.text = user.email
        }
    }
}
