package com.danil.kleshchin.tvseries.screens.popular

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
    private val KEY_CURRENT_PAGE_NUMBER = "KEY_CURRENT_PAGE_NUMBER"
    private val KEY_PAGES_COUNT = "KEY_PAGES_COUNT"

    @Inject
    lateinit var tvShowPopularPresenter: TvShowPopularContract.Presenter

    private var _binding: FragmentTvShowPopularBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as TvShowApplication).initTvShowPopularComponent(this)
        (activity?.application as TvShowApplication).getTvShowPopularComponent().inject(this)

        _binding = FragmentTvShowPopularBinding.inflate(inflater, container, false)

        initViewListeners()

        tvShowPopularPresenter.subscribe(this, getStoredState(savedInstanceState))
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        storeState(outState, tvShowPopularPresenter.getState())
    }

    override fun onStop() {
        super.onStop()
        _binding = null
        tvShowPopularPresenter.unsubscribe()
    }

    override fun showTvShowPopularList(tvShowPopularList: List<TvShowPopular>) {
        binding.apply {
            tvShowListView.adapter =
                TvShowPopularListAdapter(tvShowPopularList, this@TvShowPopularFragment)
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

    override fun showHideRetryView(hide: Boolean) {
        binding.apply {
            if (hide) {
                emptyText.visibility = View.GONE
                emptyButton.visibility = View.GONE
                tvShowListView.visibility = View.VISIBLE
            } else {
                emptyText.visibility = View.VISIBLE
                emptyButton.visibility = View.VISIBLE
                tvShowListView.visibility = View.GONE
            }
        }
    }

    override fun showDetailedScreen(tvShowPopular: TvShowPopular) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        initTvShowDetailedFragment(context, tvShowPopular)
    }

    private fun initViewListeners() {
        binding.apply {
            emptyButton.setOnClickListener {
                tvShowPopularPresenter.onRetrySelected()
            }
            tvShowListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!tvShowListView.canScrollVertically(1)) {
                        tvShowPopularPresenter.onFullTvShowListScrolled()
                    }
                }
            })
        }
    }

    private fun storeState(outState: Bundle, state: TvShowPopularContract.State) =
        outState.apply {
            putInt(KEY_CURRENT_PAGE_NUMBER, state.getCurrentPageNumber())
            putInt(KEY_PAGES_COUNT, state.getPagesCount())
        }

    private fun getStoredState(savedInstanceState: Bundle?): TvShowPopularContract.State? {
        if (savedInstanceState == null) {
            return null
        }
        val pageNumber = savedInstanceState.getInt(KEY_CURRENT_PAGE_NUMBER)
        val pagesCount = savedInstanceState.getInt(KEY_PAGES_COUNT)
        return TvShowPopularState(pageNumber, pagesCount)
    }

    //TODO where should I init this component? Mb create some helper?
    private fun initTvShowDetailedFragment(
        context: FragmentActivity,
        tvShowPopular: TvShowPopular
    ) {
        val tvShowDetailedFragment = TvShowDetailedFragment.newInstance(tvShowPopular)
        context.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, tvShowDetailedFragment)
            .addToBackStack("TvShowDetailedFragment")
            .commit()
    }
}
