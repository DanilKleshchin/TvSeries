package com.danil.kleshchin.tvseries.screens.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.tvseries.R
import com.danil.kleshchin.tvseries.databinding.ItemTvShowPopularListBinding
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.squareup.picasso.Picasso

class TvShowPopularListAdapter(
    private var tvShowPopularList: List<TvShowPopular>,
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
            ItemTvShowPopularListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowPopularViewHolder(binding, tvShowPopularClickListener)
    }

    override fun onBindViewHolder(
        holder: TvShowPopularViewHolder,
        position: Int
    ) {
        holder.bind(tvShowPopularList[position])
    }

    override fun onViewRecycled(holder: TvShowPopularViewHolder) {
        holder.getBinding().icon.let {
            Picasso.get().cancelRequest(it)
        }
        super.onViewRecycled(holder)
    }

    fun updateTvShowPopularList(tvShowPopularList: List<TvShowPopular>) {
        this.tvShowPopularList = tvShowPopularList
    }

    class TvShowPopularViewHolder(
        private val binding: ItemTvShowPopularListBinding,
        private val clickListener: OnTvShowPopularClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun getBinding() = binding

        fun bind(tvShowPopular: TvShowPopular) {
            binding.apply {
                val resources = binding.root.resources
                val networkString =
                    String.format(resources.getString(R.string.network), tvShowPopular.network, tvShowPopular.country)
                val startDateString =
                    String.format(resources.getString(R.string.start_date), tvShowPopular.startDate)
                val statusString =
                    String.format(resources.getString(R.string.status), tvShowPopular.status)

                name.text = tvShowPopular.name
                network.text = networkString
                startDate.text = startDateString
                status.text = statusString

                Picasso.get().load(tvShowPopular.iconUrl).into(icon)

                cardView.setOnClickListener { clickListener.onTvShowClick(tvShowPopular) }
            }
        }
    }
}
