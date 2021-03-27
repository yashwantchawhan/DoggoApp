package com.yashwant.doggo_api_ui.view.subbreed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yashwant.doggo_api_bridge.imageloader.ImageLoader
import com.yashwant.doggo_api_ui.R
import com.yashwant.doggo_api_ui.view.KEY_URL


class SubBreedListAdapter(private val imageLoader: ImageLoader) : RecyclerView.Adapter<SubBreedListAdapter.SubBreedListViewHolder>() {

    private lateinit var subBreedListItemClickListener: SubBreedListItemClickListener
    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubBreedListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.sub_breed_list_item, parent, false)
        return SubBreedListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubBreedListViewHolder, position: Int) {
        val url = differ.currentList[position]
        imageLoader.loadImage(holder.imageView,url,R.drawable.ic_doggo)
        holder.itemView.setOnClickListener {
            subBreedListItemClickListener.onSubBreedItemClick(url)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class SubBreedListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: AppCompatImageView = itemView.findViewById(R.id.doggoImage)
    }

    fun submitList(list: List<String>) {
        differ.submitList(list)
    }
    fun setOnSubBreedListItemClickListener(subBreedListItemClickListener: SubBreedListItemClickListener) {
        this.subBreedListItemClickListener = subBreedListItemClickListener
    }
}

interface SubBreedListItemClickListener {
    fun onSubBreedItemClick(url: String)
}
