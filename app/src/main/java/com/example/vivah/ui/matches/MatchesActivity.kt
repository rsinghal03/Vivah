package com.example.vivah.ui.matches

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vivah.R
import com.example.vivah.databinding.ActivityMatchesBinding
import com.example.vivah.extensions.visible
import com.example.vivah.ui.base.BaseActivity
import com.example.vivah.ui.base.BaseViewModel
import com.example.vivah.util.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MatchesActivity : BaseActivity<ActivityMatchesBinding>() {

    private val viewModel: MatchesViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = viewModel

    override var layoutId: Int = R.layout.activity_matches

    private val matchesAdapter by lazy { MatchesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("flow  oncreate")
        setRecyclerView()
        initObserver()
    }

    private fun setRecyclerView() {
        viewBinding?.matchesRecyclerView?.run {
            layoutManager = LinearLayoutManager(this@MatchesActivity, RecyclerView.VERTICAL, false)
            adapter = matchesAdapter
        }
        matchesAdapter.run {
            acceptBtnClick = { viewModel.updateStatus(it, true) }
            declineBtnClick = { viewModel.updateStatus(it, false) }
        }
    }

    private fun initObserver() {
        matchesObserver()
    }

    private fun matchesObserver() {
        Timber.d("flow fetch matches")
        viewModel.getMatches().observe(this, {
            Timber.d("flow resource received")
            when (it) {
                is Resource.Success -> {
                    viewModel.showProgressBar.value = false
                    viewBinding?.matchesRecyclerView?.visible()
                    matchesAdapter.updateList(it.data)
                }
                is Resource.Error -> {
                    viewModel.showProgressBar.value = false
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        Timber.d("flow onstop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("flow onDestroy")
    }
}