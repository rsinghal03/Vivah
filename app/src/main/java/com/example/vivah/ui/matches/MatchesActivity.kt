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

class MatchesActivity : BaseActivity<ActivityMatchesBinding>() {

    private val viewModel: MatchesViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = viewModel

    override var layoutId: Int = R.layout.activity_matches

    private val matchesAdapter by lazy { MatchesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRecyclerView()
        fetchMatches()
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

    private fun fetchMatches() {
        viewModel.getMatches().observe(this, {
            when (it) {
                is Resource.Success -> {
                    viewModel.showProgressBar.value = false
                    viewBinding?.matchesRecyclerView?.visible()
                    matchesAdapter.updateList(it.data)
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    viewModel.showProgressBar.value = false
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                }
            }
        })
    }
}