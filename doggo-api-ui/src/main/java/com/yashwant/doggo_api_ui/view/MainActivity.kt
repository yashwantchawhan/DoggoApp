package com.yashwant.doggo_api_ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yashwant.doggo_api_ui.R
import com.yashwant.doggo_api_ui.di.DaggerMainActivityComponent
import com.yashwant.doggo_api_ui.di.DoggoDependenciesProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMainActivityComponent.builder()
            .dependencies((application as DoggoDependenciesProvider).doggoCommonDependencies())
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}