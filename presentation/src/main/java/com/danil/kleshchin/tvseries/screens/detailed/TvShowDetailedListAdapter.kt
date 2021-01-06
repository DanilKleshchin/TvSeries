package com.danil.kleshchin.tvseries.screens.detailed

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.tvseries.R
import com.danil.kleshchin.tvseries.databinding.ItemTvShowDetailedListBinding
import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import com.squareup.picasso.Picasso

class TvShowDetailedListAdapter(
    private val tvShowDetailedList: List<TvShowDetailed>,
    private val context: Context,
    private val descriptionMoreClickListener: OnDescriptionMoreClickListener
) : RecyclerView.Adapter<TvShowDetailedListAdapter.TvShowDetailedViewHolder>() {

    interface OnDescriptionMoreClickListener {
        fun onMoreClick(tvShowDetailed: TvShowDetailed)
    }

    override fun getItemCount(): Int = tvShowDetailedList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowDetailedViewHolder {
        val binding =
            ItemTvShowDetailedListBinding.inflate(LayoutInflater.from(context), parent, false)
        return TvShowDetailedViewHolder(binding, descriptionMoreClickListener)
    }

    override fun onBindViewHolder(
        holder: TvShowDetailedViewHolder,
        position: Int
    ) {
        holder.bind(tvShowDetailedList[position])
    }

    class TvShowDetailedViewHolder(
        private val binding: ItemTvShowDetailedListBinding,
        private val moreClickListener: OnDescriptionMoreClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShowDetailed: TvShowDetailed) {
            binding.apply {
                val resources = binding.root.resources
                val networkString =
                    String.format(resources.getString(R.string.network), tvShowDetailed.network)
                val countryString =
                    String.format(resources.getString(R.string.country), tvShowDetailed.country)
                val startDateString =
                    String.format(resources.getString(R.string.start_date), tvShowDetailed.startDate)
                val statusString =
                    String.format(resources.getString(R.string.status), tvShowDetailed.status)

                description.text = tvShowDetailed.description
                network.text = networkString
                country.text = countryString
                startDate.text = startDateString
                status.text = statusString

                Picasso.get().load(tvShowDetailed.iconUrl).into(icon)

                descriptionMore.setOnClickListener { moreClickListener.onMoreClick(tvShowDetailed) }
            }
        }
    }
}
