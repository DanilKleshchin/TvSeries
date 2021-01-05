package com.danil.kleshchin.tvseries.screens.popular

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.danil.kleshchin.tvseries.databinding.FragmentTvShowPopularBinding
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import javax.inject.Inject

class TvShowPopularFragment: Fragment(), TvShowPopularContract.View, TvShowPopularNavigator,
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
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        tvShowPopularPresenter.setView(this)
        tvShowPopularPresenter.onAttach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showTvShowPopularList(tvShowPopularList: List<TvShowPopular>) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        binding.tvShowListView.adapter = TvShowPopularListAdapter(tvShowPopularList, context, this)
    }

    override fun onTvShowClick(tvShowPopular: TvShowPopular) {

    }

    override fun showLoadingView() {

    }

    override fun hideLoadingView() {

    }

    override fun showRetry() {

    }

    override fun hideRetry() {

    }

    override fun showDetailedScreen(tvShowPopular: TvShowPopular) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
    }

    //TODO where should I init feed component?
    /*private fun initFeedView(context: FragmentActivity, section: Section) {
        val feedFragment = FeedFragment.newInstance(section)
        (context.application as NYTimesRSSFeedsApp).initFeedComponent(feedFragment)
        (context.application as NYTimesRSSFeedsApp).getFeedComponent().inject(feedFragment)
        context.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, feedFragment)
            .commitNow()
    }*/
}
