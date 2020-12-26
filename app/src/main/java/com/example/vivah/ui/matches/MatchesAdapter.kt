package com.example.vivah.ui.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vivah.R
import com.example.vivah.databinding.MatchesItemBinding
import com.example.vivah.db.model.MatchesResponse

class MatchesAdapter : RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder>() {

    private var matchesList: List<MatchesResponse.Result> = arrayListOf<MatchesResponse.Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: MatchesItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.matches_item, parent, false)
        return MatchesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        holder.itemBinding.name.text = matchesList[position].name.first
        Glide.with(holder.itemView)
            .load(matchesList[position].picture.large)
            .centerCrop()
            .placeholder(R.drawable.place_holder)
            .into(holder.itemBinding.photo)
    }

    override fun getItemCount(): Int = matchesList.size

    fun updateList(list: List<MatchesResponse.Result>?) {
        val diff = DiffUtil.calculateDiff(matchesDiffCallback(matchesList, list ?: matchesList))
        diff.dispatchUpdatesTo(this)
        list?.let { matchesList = it }
    }

    inner class MatchesViewHolder(val itemBinding: MatchesItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }

    private fun matchesDiffCallback(
        oldList: List<MatchesResponse.Result>,
        newList: List<MatchesResponse.Result>
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
}