package com.danil.kleshchin.tvseries.screens.detailed

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.danil.kleshchin.tvseries.R
import com.danil.kleshchin.tvseries.TvShowApplication
import com.danil.kleshchin.tvseries.databinding.FragmentTvShowDetailedBinding
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.fromHtml
import com.danil.kleshchin.tvseries.screens.detailed.models.TvShowDetailedModel
import com.squareup.picasso.Picasso
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TvShowDetailedFragment : Fragment(), TvShowDetailedContract.View, TvShowDetailedNavigator {

    private val ERROR_LOG_MESSAGE = "TvShowDetailedFragment fragment wasn't attached."
    private val KEY_TV_SHOW_POPULAR = "KEY_TV_SHOW_POPULAR"

    private val FRAGMENT_FINISHING_DELAY = 100L

    @Inject
    lateinit var tvShowDetailedPresenter: TvShowDetailedContract.Presenter

    private var episodesPicturesPageIndicators = arrayListOf<ImageView>()

    private var _binding: FragmentTvShowDetailedBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val KEY_TV_SHOW_POPULAR = "KEY_TV_SHOW_POPULAR"

        fun newInstance(tvShowPopular: TvShowPopular): TvShowDetailedFragment {
            val feedFragment = TvShowDetailedFragment()
            val args = Bundle()
            args.putSerializable(KEY_TV_SHOW_POPULAR, tvShowPopular)
            feedFragment.arguments = args
            return feedFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as TvShowApplication).initTvShowDetailedComponent(this)
        (activity?.application as TvShowApplication).getTvShowDetailedComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowDetailedBinding.inflate(inflater, container, false)
        setBackPressedCallback()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowDetailedPresenter.subscribe(this, getStoredState(savedInstanceState))

        binding.apply {
            emptyButton.setOnClickListener { tvShowDetailedPresenter.onRefreshSelected() }
            backButton.setOnClickListener {
                Completable.timer(
                    FRAGMENT_FINISHING_DELAY,
                    TimeUnit.MILLISECONDS,
                    AndroidSchedulers.mainThread()
                )
                    .subscribe { finish() }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        storeState(outState, tvShowDetailedPresenter.getState())
    }

    override fun onStop() {
        super.onStop()
        episodesPicturesPageIndicators = arrayListOf()
        _binding = null
        tvShowDetailedPresenter.unsubscribe()
    }

    override fun showTvShowDetailed(tvShowDetailed: TvShowDetailedModel) {
        bind(tvShowDetailed)
    }

    override fun showHideLoadingView(hide: Boolean) {
        if (hide) {
            binding.loadingView.visibility = View.GONE
        } else {
            binding.loadingView.visibility = View.VISIBLE
        }
    }

    override fun showHideRetryView(hide: Boolean) {
        binding.apply {
            if (hide) {
                scrollView.visibility = View.VISIBLE
                emptyButton.visibility = View.GONE
                emptyText.visibility = View.GONE
            } else {
                scrollView.visibility = View.GONE
                emptyButton.visibility = View.VISIBLE
                emptyText.visibility = View.VISIBLE
            }
        }
    }

    override fun showWebPage(url: String) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        val primaryColor = ContextCompat.getColor(context, R.color.colorPrimary)
        val toolbarColor = ContextCompat.getColor(context, R.color.colorToolBar)
        val secondaryToolbarColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        val params = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(toolbarColor)
            .setSecondaryToolbarColor(secondaryToolbarColor)
            .setNavigationBarColor(primaryColor)
            .build()
        CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(params)
            .build()
            .launchUrl(context, Uri.parse(url))
    }

    private fun bind(tvShowDetailed: TvShowDetailedModel) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        binding.apply {
            val resources = binding.root.resources
            val networkString =
                String.format(
                    resources.getString(R.string.network),
                    tvShowDetailed.network,
                    tvShowDetailed.country
                )
            val startDateString =
                String.format(resources.getString(R.string.start_date), tvShowDetailed.startDate)
            val statusString =
                String.format(resources.getString(R.string.status), tvShowDetailed.status)
            val ratingString =
                String.format(
                    resources.getString(R.string.rating),
                    tvShowDetailed.rating,
                    tvShowDetailed.ratingCount
                )
            val runtimeString =
                String.format(resources.getString(R.string.runtime), tvShowDetailed.runtime)

            name.text = tvShowDetailed.name
            name.isSelected = true
            description.text = tvShowDetailed.description.fromHtml()
            network.text = networkString
            startDate.text = startDateString
            status.text = statusString
            rating.text = ratingString
            runtime.text = runtimeString
            genres.text = tvShowDetailed.genres

            Picasso.get().load(tvShowDetailed.iconUrl).into(icon)

            sliderEpisodesPictures.apply {
                offscreenPageLimit = 1
                adapter = TvShowDetailedSliderAdapter(tvShowDetailed.episodesPictures)
            }

            initPageIndicators(tvShowDetailed.episodesPictures.size, context)
            initViewListeners(tvShowDetailed)
        }
    }

    private fun initPageIndicators(pageCount: Int, context: Context) {
        episodesPicturesPageIndicators = ArrayList(pageCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.CENTER
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in 0 until pageCount) {
            val indicator = ImageView(context)
            setPageIndicatorColor(R.drawable.background_pager_inidcator_default, indicator)
            indicator.layoutParams = layoutParams
            binding.sliderPageIndicator.addView(indicator)
            episodesPicturesPageIndicators.add(indicator)
        }
        setCurrentPageIndicator(0)
    }

    private fun setCurrentPageIndicator(indicatorPosition: Int) {
        for (i in 0 until episodesPicturesPageIndicators.size) {
            if (i == indicatorPosition) {
                setPageIndicatorColor(
                    R.drawable.background_pager_inidcator_selected,
                    episodesPicturesPageIndicators[i]
                )
            } else {
                setPageIndicatorColor(
                    R.drawable.background_pager_inidcator_default,
                    episodesPicturesPageIndicators[i]
                )
            }
        }
    }

    private fun setPageIndicatorColor(@DrawableRes color: Int, view: ImageView) {
        view.setImageDrawable(ContextCompat.getDrawable(view.context, color))
    }

    private fun initViewListeners(tvShowDetailed: TvShowDetailedModel) {
        binding.apply {
            //TODO change this
            readMore.setOnClickListener {
                if (readMore.text == getString(R.string.read_more)) {
                    description.ellipsize = null
                    description.maxLines = Int.MAX_VALUE
                    readMore.text = getString(R.string.read_less)
                } else {
                    readMore.text = getString(R.string.read_more)
                    description.ellipsize = TextUtils.TruncateAt.END
                    description.maxLines = 4
                }
                description.text = tvShowDetailed.description
            }

            buttonWebsite.setOnClickListener {
                tvShowDetailedPresenter.onWebPageSelected(tvShowDetailed)
            }

            buttonEpisodes.setOnClickListener {

            }

            sliderEpisodesPictures.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentPageIndicator(position)
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

    private fun storeState(outState: Bundle, state: TvShowDetailedContract.State) =
        outState.putSerializable(KEY_TV_SHOW_POPULAR, state.getTvShowPopular())

    private fun getStoredState(savedInstanceState: Bundle?): TvShowDetailedContract.State {
        val tvShowPopular = if (savedInstanceState == null) {
            getTvShowPopular()
        } else {
            savedInstanceState.getSerializable(KEY_TV_SHOW_POPULAR) as TvShowPopular
        }
        return TvShowDetailedState(tvShowPopular)
    }

    private fun getTvShowPopular(): TvShowPopular {
        return arguments?.getSerializable(KEY_TV_SHOW_POPULAR) as TvShowPopular?
            ?: throw NullPointerException("TvShowPopular is null")
    }

    private fun finish() {
        activity?.supportFragmentManager?.popBackStack()
    }
}
