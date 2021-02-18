package com.danil.kleshchin.tvseries.screens.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.tvseries.TvShowApplication
import com.danil.kleshchin.tvseries.databinding.FragmentTvShowPopularBinding
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import javax.inject.Inject

class TvShowPopularFragment : Fragment(), TvShowPopularContract.View,
    TvShowPopularListAdapter.OnTvShowPopularClickListener {

    private val KEY_CURRENT_PAGE_NUMBER = "KEY_CURRENT_PAGE_NUMBER"
    private val KEY_PAGES_COUNT = "KEY_PAGES_COUNT"
    private val KEY_WAS_LIST_LOADED = "KEY_WAS_LIST_LOADED"

    @Inject
    lateinit var tvShowPopularPresenter: TvShowPopularContract.Presenter

    private var _binding: FragmentTvShowPopularBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TvShowApplication.INSTANCE.initTvShowPopularComponent()
        TvShowApplication.INSTANCE.getTvShowPopularComponent().inject(this)
        tvShowPopularPresenter.subscribe(this, getStoredState(savedInstanceState))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowPopularBinding.inflate(inflater, container, false)

        initViewListeners()
        setBackPressedCallback()

        tvShowPopularPresenter.onAttach()

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        storeState(outState, tvShowPopularPresenter.getState())
    }

    override fun onPause() {
        super.onPause()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
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

    private fun setBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun storeState(outState: Bundle, state: TvShowPopularContract.State) =
        outState.apply {
            putInt(KEY_CURRENT_PAGE_NUMBER, state.getCurrentPageNumber())
            putInt(KEY_PAGES_COUNT, state.getPagesCount())
            putBoolean(KEY_WAS_LIST_LOADED, state.getWasTvShowPopularListLoaded())
        }

    private fun getStoredState(savedInstanceState: Bundle?): TvShowPopularContract.State? {
        if (savedInstanceState == null) {
            return null
        }
        val pageNumber = savedInstanceState.getInt(KEY_CURRENT_PAGE_NUMBER)
        val pagesCount = savedInstanceState.getInt(KEY_PAGES_COUNT)
        val wasListLoaded = savedInstanceState.getBoolean(KEY_WAS_LIST_LOADED)
        return TvShowPopularState(pageNumber, pagesCount, wasListLoaded)
    }

    private fun finish() {
        activity?.finish()
    }
}
