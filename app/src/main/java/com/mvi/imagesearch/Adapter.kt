package com.mvi.imagesearch

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvi.imagesearch.model.ApiModel
import com.mvi.imagesearch.ui.ImageSearchFragment
import kotlinx.android.synthetic.main.item_grid.view.*

class Adapter(private val interaction: Interaction ?= null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ApiModel>() {

        override fun areItemsTheSame(oldItem: ApiModel, newItem: ApiModel): Boolean {
           return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ApiModel, newItem: ApiModel): Boolean {
            return  oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_grid,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ApiModel>) {
        differ.submitList(list)
    }
    fun clear() {
        val size = differ.currentList.size
        for(i in 0..size){
            differ.currentList.removeAt(i)
        }

    }

    class ImageViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ApiModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            Glide.with(itemView.context)
                .load(item.thumb)
                .apply(RequestOptions().override(600, 200))
                .error(R.mipmap.ic_launcher_round)
                .into(itemView.image)

           // TODO("bind view with data")


        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ApiModel)
    }

    //Interaction class as generic
    /*interface Interaction<T> {
        fun onItemSelected(position: Int, item: T)
    }*/

}