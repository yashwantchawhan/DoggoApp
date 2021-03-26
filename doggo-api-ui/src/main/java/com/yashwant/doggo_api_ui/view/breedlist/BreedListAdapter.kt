package com.yashwant.doggo_api_ui.view.breedlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yashwant.doggo_api_ui.R

class BreedListAdapter : RecyclerView.Adapter<BreedListAdapter.DoggoListHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoggoListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.breed_list_item, parent, false);
        return DoggoListHolder(view)
    }

    override fun onBindViewHolder(holder: DoggoListHolder, position: Int) {
        holder.textView.text = differ.currentList.get(position)
        holder.itemView.setOnClickListener {
            val bundle = Bundle().also {
                it.putString("breedName", differ.currentList.get(position))
            }
            holder.textView.findNavController().navigate(
                R.id.action_breedListFragment_to_subBreedFragment,
                bundle,
                navOptions { // Use the Kotlin DSL for building NavOptions
                    anim {
                        enter = android.R.animator.fade_in
                        exit = android.R.animator.fade_out
                    }
                }
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    class DoggoListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.doggoName)
    }

    fun setDoggoList(list: List<String>) {
        differ.submitList(list)
    }

}
