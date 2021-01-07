package com.danil.kleshchin.tvseries.screens.detailed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.tvseries.databinding.ItemEpisodesViewpagerBinding
import com.squareup.picasso.Picasso

class TvShowDetailedSliderAdapter(
    private val sliderImageUrlsList: List<String>
) : RecyclerView.Adapter<TvShowDetailedSliderAdapter.TvShowDetailedSliderViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowDetailedSliderViewHolder {
        val binding =
            ItemEpisodesViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowDetailedSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowDetailedSliderViewHolder, position: Int) {
        holder.bind(sliderImageUrlsList[position])
    }

    override fun getItemCount(): Int {
        return sliderImageUrlsList.size
    }

    class TvShowDetailedSliderViewHolder(
        private val binding: ItemEpisodesViewpagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            Picasso.get().load(imageUrl).into(binding.episodesPicture)
        }

    }
}
