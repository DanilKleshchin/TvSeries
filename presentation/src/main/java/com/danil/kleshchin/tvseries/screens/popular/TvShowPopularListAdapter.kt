package com.danil.kleshchin.tvseries.screens.popular

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.tvseries.databinding.ItemTvShowPopularListBinding
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular

class TvShowPopularListAdapter(
    private val tvShowPopularList: List<TvShowPopular>,
    private val context: Context,
    private val tvShowPopularClickListener: OnTvShowPopularClickListener
) : RecyclerView.Adapter<TvShowPopularListAdapter.TvShowPopularViewHolder>() {

    interface OnTvShowPopularClickListener {
        fun onTvShowClick(tvShowPopular: TvShowPopular)
    }

    override fun getItemCount(): Int = tvShowPopularList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowPopularViewHolder {
        val binding = ItemTvShowPopularListBinding.inflate(LayoutInflater.from(context), parent, false)
        return TvShowPopularViewHolder(binding, tvShowPopularClickListener)
    }

    override fun onBindViewHolder(
        holder: TvShowPopularViewHolder,
        position: Int
    ) {
        holder.bind(tvShowPopularList[position])
    }

    class TvShowPopularViewHolder(
        private val binding: ItemTvShowPopularListBinding,
        private val clickListener: OnTvShowPopularClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShowPopular: TvShowPopular) {
            binding.apply {
                name.text = tvShowPopular.name
                root.setOnClickListener { clickListener.onTvShowClick(tvShowPopular) }
            }
        }
    }
}
