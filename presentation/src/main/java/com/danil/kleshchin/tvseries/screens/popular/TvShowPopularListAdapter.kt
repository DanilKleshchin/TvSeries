package com.danil.kleshchin.tvseries.screens.popular

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.tvseries.R
import com.danil.kleshchin.tvseries.databinding.ItemTvShowPopularListBinding
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.squareup.picasso.Picasso

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
        val binding =
            ItemTvShowPopularListBinding.inflate(LayoutInflater.from(context), parent, false)
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
                val resources = binding.root.resources
                val networkString =
                    String.format(resources.getString(R.string.network), tvShowPopular.network)
                val countryString =
                    String.format(resources.getString(R.string.country), tvShowPopular.country)
                val startDateString =
                    String.format(resources.getString(R.string.start_date), tvShowPopular.startDate)
                val statusString =
                    String.format(resources.getString(R.string.status), tvShowPopular.status)

                name.text = tvShowPopular.name
                network.text = networkString
                country.text = countryString
                startDate.text = startDateString
                status.text = statusString

                Picasso.get().load(tvShowPopular.iconUrl).into(icon)

                root.setOnClickListener { clickListener.onTvShowClick(tvShowPopular) }
            }
        }
    }
}
