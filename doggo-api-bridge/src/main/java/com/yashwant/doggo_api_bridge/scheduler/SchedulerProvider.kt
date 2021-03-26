package com.yashwant.doggo_api_bridge.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class SchedulerProvider {

    open fun io(): Scheduler = Schedulers.io()

    open fun ui(): Scheduler = AndroidSchedulers.mainThread()
}

// mock scheduler for testing viewmodel or api calls
class TestSchedulerProvider : SchedulerProvider() {

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun ui(): Scheduler = Schedulers.trampoline()
}
