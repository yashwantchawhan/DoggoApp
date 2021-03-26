package com.yashwant.doggoapp.app

import android.app.Application
import com.yashwant.doggo_api_ui.di.DoggoCommonDependencies
import com.yashwant.doggo_api_ui.di.DoggoDependenciesProvider
import com.yashwant.doggoapp.di.AppComponent
import com.yashwant.doggoapp.di.DaggerAppComponent

class DoggoApp : Application(), DoggoDependenciesProvider {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        setupDi()
    }

    private fun setupDi() {
        component = DaggerAppComponent.builder()
            .application(this)
            .build()
        component.inject(this)
    }

    // the dependencies provide from App level
    override fun doggoCommonDependencies(): DoggoCommonDependencies = component
}