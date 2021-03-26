package com.yashwant.doggo_api_ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yashwant.doggo_api_ui.R
import com.yashwant.doggo_api_ui.di.DaggerMainActivityComponent
import com.yashwant.doggo_api_ui.di.DoggoDependenciesProvider

internal const val KEY_URL = "url"
internal const val KEY_BREED_NAME = "breedName"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMainActivityComponent.factory()
            .create((application as DoggoDependenciesProvider).doggoCommonDependencies())
            .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}