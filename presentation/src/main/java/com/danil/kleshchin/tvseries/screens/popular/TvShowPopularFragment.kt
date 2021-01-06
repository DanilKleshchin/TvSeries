package com.danil.kleshchin.tvseries.screens.popular

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.tvseries.R
import com.danil.kleshchin.tvseries.TvShowApplication
import com.danil.kleshchin.tvseries.databinding.FragmentTvShowPopularBinding
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.screens.detailed.TvShowDetailedFragment
import javax.inject.Inject

class TvShowPopularFragment : Fragment(), TvShowPopularContract.View, TvShowPopularNavigator,
    TvShowPopularListAdapter.OnTvShowPopularClickListener {

    private val ERROR_LOG_MESSAGE = "TvShowPopularFragment fragment wasn't attached."

    @Inject
    lateinit var tvShowPopularPresenter: TvShowPopularContract.Presenter

    private var _binding: FragmentTvShowPopularBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowPopularBinding.inflate(inflater, container, false)
        tvShowPopularPresenter.setView(this)
        tvShowPopularPresenter.onAttach()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showTvShowPopularList(tvShowPopularList: List<TvShowPopular>) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        binding.apply {
            tvShowListView.adapter = TvShowPopularListAdapter(tvShowPopularList, context, this@TvShowPopularFragment)
            tvShowListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!tvShowListView.canScrollVertically(1)) {
                        tvShowPopularPresenter.onFullTvShowListScrolled()
                    }
                }
            })
        }
    }

    override fun updateTvShowPopularList(tvShowPopularList: List<TvShowPopular>) {
        val adapter = binding.tvShowListView.adapter as TvShowPopularListAdapter
        val oldSize = adapter.itemCount
        val newSize = tvShowPopularList.size
        adapter.updateTvShowPopularList(tvShowPopularList)
        adapter.notifyItemRangeInserted(oldSize, newSize)
    }

    override fun onTvShowClick(tvShowPopular: TvShowPopular) {
        tvShowPopularPresenter.onTvShowPopularSelected(tvShowPopular)
    }

    override fun showHideLoadingView(hide: Boolean) {
        if (hide) {
            binding.loadingView.visibility = View.GONE
        } else {
            binding.loadingView.visibility = View.VISIBLE
        }
    }

    override fun showHideBottomLoadingView(hide: Boolean) {
        if (hide) {
            binding.bottomLoadingView.visibility = View.GONE
        } else {
            binding.bottomLoadingView.visibility = View.VISIBLE
        }
    }

    override fun showRetry() {

    }

    override fun hideRetry() {

    }

    override fun showDetailedScreen(tvShowPopular: TvShowPopular) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        initTvShowDetailedFragment(context, tvShowPopular)
    }

    //TODO where should I init this component?
    private fun initTvShowDetailedFragment(
        context: FragmentActivity,
        tvShowPopular: TvShowPopular
    ) {
        val tvShowDetailedFragment = TvShowDetailedFragment.newInstance(tvShowPopular)
        (context.application as TvShowApplication).initTvShowDetailedComponent(
            tvShowDetailedFragment
        )
        (context.application as TvShowApplication).getTvShowDetailedComponent()
            .inject(tvShowDetailedFragment)
        context.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, tvShowDetailedFragment)
            .commitNow()
    }
}
