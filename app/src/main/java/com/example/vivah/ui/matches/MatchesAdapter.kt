package com.example.vivah.ui.matches

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vivah.R
import com.example.vivah.databinding.MatchesItemBinding
import com.example.vivah.db.entity.Matches
import com.example.vivah.extensions.gone
import com.example.vivah.extensions.visible

class MatchesAdapter : RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder>() {

    private var matchesList: List<Matches> = arrayListOf()

    var acceptBtnClick: ((item: Matches) -> Unit)? = null
    var declineBtnClick: ((item: Matches) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: MatchesItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.matches_item, parent, false)
        return MatchesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = matchesList.size

    fun updateList(list: List<Matches>?) {
        val diff = DiffUtil.calculateDiff(matchesDiffCallback(matchesList, list ?: matchesList))
        diff.dispatchUpdatesTo(this)
        list?.let { matchesList = it }
    }

    private fun matchesDiffCallback(
        oldList: List<Matches>,
        newList: List<Matches>
    ): DiffUtil.Callback {
        return object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition].id == newList[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }
        }
    }

    inner class MatchesViewHolder(private val itemBinding: MatchesItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val context: Context = itemBinding.root.context

        init {
            itemBinding.accept.setOnClickListener {
                acceptBtnClick?.invoke(matchesList[adapterPosition])
            }
            itemBinding.decline.setOnClickListener {
                declineBtnClick?.invoke(matchesList[adapterPosition])
            }
        }

        fun bind(position: Int) {
            itemBinding.name.text = matchesList[position].name.let { "${it.first} ${it.last}" }
            itemBinding.personDetails.text =
                matchesList[position].let { "${it.age} ${"\n"}${it.city}, ${it.state}" }
            Glide.with(itemView)
                .load(matchesList[position].photo)
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(itemBinding.photo)
            val status = matchesList[position].statusIsAccepted
            if (status == true) {
                handleVisibility()
                itemBinding.status.text = context.resources.getString(R.string.request_accepted)
            }
            if (status == false) {
                handleVisibility()
                itemBinding.status.text = context.resources.getString(R.string.request_decline)
            }
        }

        private fun handleVisibility() {
            itemBinding.status.visible()
            itemBinding.accept.gone()
            itemBinding.decline.gone()
        }
    }
}