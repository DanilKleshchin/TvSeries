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
import com.danil.kleshchin.tvseries.databinding.FragmentTvShowDetailedBinding
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.danil.kleshchin.tvseries.screens.detailed.models.TvShowDetailedModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class TvShowDetailedFragment : Fragment(), TvShowDetailedContract.View, TvShowDetailedNavigator {

    private val ERROR_LOG_MESSAGE = "TvShowDetailedFragment fragment wasn't attached."

    @Inject
    lateinit var tvShowDetailedPresenter: TvShowDetailedContract.Presenter

    private var tvShowPopular: TvShowPopular? = null

    private lateinit var episodesPicturesPageIndicators: ArrayList<ImageView>

    private var _binding: FragmentTvShowDetailedBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val KEY_SECTION = "KEY_SECTION"
        private val INSTANCE_STATE_PARAM_SECTION = "STATE_PARAM_SECTION"

        fun newInstance(tvShowPopular: TvShowPopular): TvShowDetailedFragment {
            val feedFragment = TvShowDetailedFragment()
            val args = Bundle()
            args.putSerializable(KEY_SECTION, tvShowPopular)
            feedFragment.arguments = args
            return feedFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeFragment(savedInstanceState)

        _binding = FragmentTvShowDetailedBinding.inflate(inflater, container, false)
        setBackPressedCallback()
        tvShowDetailedPresenter.setView(this)
        tvShowDetailedPresenter.onAttach()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowDetailedPresenter.setView(this)
        tvShowDetailedPresenter.onAttach()
        initPresenterForTvShowPopular()

        binding.backButton.setOnClickListener { finish() }
    }

    override fun onStop() {
        super.onStop()
        tvShowDetailedPresenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showTvShowDetailedName(name: String) {
        binding.name.text = name
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

    override fun showRetry() {

    }

    override fun hideRetry() {

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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(INSTANCE_STATE_PARAM_SECTION, tvShowPopular)
        super.onSaveInstanceState(outState)
    }

    private fun bind(tvShowDetailed: TvShowDetailedModel) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        binding.apply {
            val resources = binding.root.resources
            val networkString =
                String.format(resources.getString(R.string.network), tvShowDetailed.network, tvShowDetailed.country)
            val startDateString =
                String.format(resources.getString(R.string.start_date), tvShowDetailed.startDate)
            val statusString =
                String.format(resources.getString(R.string.status), tvShowDetailed.status)
            val ratingString =
                String.format(resources.getString(R.string.rating), tvShowDetailed.rating, tvShowDetailed.ratingCount)
            val runtimeString =
                String.format(resources.getString(R.string.runtime), tvShowDetailed.runtime)

            setViewsVisibility(View.VISIBLE)

            description.text = tvShowDetailed.description
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

            buttonWebsite.setOnClickListener{
                tvShowDetailedPresenter.onWebPageSelected(tvShowDetailed)
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

    private fun setViewsVisibility(visibility: Int) {
        binding.apply {
            description.visibility = visibility
            network.visibility = visibility
            startDate.visibility = visibility
            status.visibility = visibility
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
        view.setImageDrawable(ContextCompat.getDrawable(view.context,color))
    }

    private fun setBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun initializeFragment(savedInstanceState: Bundle?) {
        tvShowPopular = if (savedInstanceState == null) {
            getTvShowPopular()
        } else ({
            savedInstanceState.getSerializable(INSTANCE_STATE_PARAM_SECTION)
        }) as TvShowPopular?
    }

    private fun initPresenterForTvShowPopular() {
        val tvShowPopular = getTvShowPopular()
        tvShowDetailedPresenter.initialize(
            tvShowPopular ?: throw NullPointerException("Section is null")
        )
    }

    private fun getTvShowPopular(): TvShowPopular? {
        return arguments?.getSerializable(KEY_SECTION) as TvShowPopular?
    }

    private fun finish() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commitNow()
    }
}
