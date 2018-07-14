package info.xudshen.android.playground.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.rx2.asCoroutineDispatcher

/**
 * Created by xudong on 2018/7/14
 */
object AppRxSchedulers {
    val database: Scheduler = Schedulers.single()
    val disk: Scheduler = Schedulers.io()
    val network: Scheduler = Schedulers.io()
    val main: Scheduler = AndroidSchedulers.mainThread()
}

object AppCoroutineDispatchers {
    val database: CoroutineDispatcher = AppRxSchedulers.database.asCoroutineDispatcher()
    val disk: CoroutineDispatcher = AppRxSchedulers.disk.asCoroutineDispatcher()
    val network: CoroutineDispatcher = AppRxSchedulers.network.asCoroutineDispatcher()
    val main: CoroutineDispatcher = AppRxSchedulers.main.asCoroutineDispatcher()
}

object AppJobs {
    val mainapp: Job = Job()
}

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")