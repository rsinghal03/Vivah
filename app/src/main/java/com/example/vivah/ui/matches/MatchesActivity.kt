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
import com.example.vivah.util.Status
import org.koin.android.ext.android.inject

class MatchesActivity : BaseActivity<ActivityMatchesBinding>() {

    private val viewModel: MatchesViewModel by inject()

    override var getViewModel: BaseViewModel = viewModel

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

    }

    private fun fetchMatches() {
        viewModel.getMatches().observe(this, {
            viewModel.showProgressBar.value = false
            when (it.status) {
                Status.SUCCESS -> {
                    viewBinding?.matchesRecyclerView?.visible()
                    matchesAdapter.updateList(it.data)
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                }
            }
        })
    }


}