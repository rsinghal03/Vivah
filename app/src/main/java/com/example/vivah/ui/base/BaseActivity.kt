package com.example.vivah.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.vivah.R
import com.example.vivah.databinding.ActivityBaseBinding
import com.example.vivah.extensions.gone
import com.example.vivah.extensions.visible
import timber.log.Timber

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    private var baseViewModel: BaseViewModel? = null

    abstract var getViewModel: BaseViewModel

    var viewBinding: VB? = null

    abstract var layoutId: Int

    var activityBaseBinding: ActivityBaseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel = getViewModel
        activityBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        activityBaseBinding?.executePendingBindings()
        activityBaseBinding?.lifecycleOwner = this
        viewBinding = DataBindingUtil.inflate(
            layoutInflater,
            layoutId,
            activityBaseBinding?.layoutContainer,
            false
        )
        activityBaseBinding?.layoutContainer?.addView(viewBinding?.root)
        initObserver()
    }

    private fun initObserver() {
        baseViewModel?.showProgressBar?.observe(this, { isShowProgress ->
            activityBaseBinding?.frameLayout?.let {
                Timber.d("progress bar")
                if (isShowProgress) it.visible() else it.gone()
            }
        })
    }
}