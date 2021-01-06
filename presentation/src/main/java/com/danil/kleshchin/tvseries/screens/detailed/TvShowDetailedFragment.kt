package com.danil.kleshchin.tvseries.screens.detailed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.danil.kleshchin.tvseries.R
import com.danil.kleshchin.tvseries.databinding.FragmentTvShowDetailedBinding
import com.danil.kleshchin.tvseries.domain.entity.TvShowDetailed
import com.danil.kleshchin.tvseries.domain.entity.TvShowPopular
import com.squareup.picasso.Picasso
import javax.inject.Inject

class TvShowDetailedFragment : Fragment(), TvShowDetailedContract.View, TvShowDetailedNavigator {

    private val ERROR_LOG_MESSAGE = "TvShowDetailedFragment fragment wasn't attached."

    @Inject
    lateinit var tvShowDetailedPresenter: TvShowDetailedContract.Presenter

    private var tvShowPopular: TvShowPopular? = null

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowDetailedPresenter.setView(this)
        tvShowDetailedPresenter.onAttach()
        initPresenterForTvShowPopular()

        binding.backButton.setOnClickListener { finish() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setBackPressedCallback()
        tvShowDetailedPresenter.setView(this)
        tvShowDetailedPresenter.onAttach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showTvShowDetailedName(name: String) {
        binding.name.text = name
    }

    override fun showTvShowDetailed(tvShowDetailed: TvShowDetailed) {
        bind(tvShowDetailed)
    }

    override fun showLoadingView() {

    }

    override fun hideLoadingView() {

    }

    override fun showRetry() {

    }

    override fun hideRetry() {

    }

    override fun showWebPage(url: String) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(INSTANCE_STATE_PARAM_SECTION, tvShowPopular)
        super.onSaveInstanceState(outState)
    }

    private fun bind(tvShowDetailed: TvShowDetailed) {
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

            descriptionMore.setOnClickListener {
                tvShowDetailedPresenter.onDescriptionMoreSelected(
                    tvShowDetailed
                )
            }
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
