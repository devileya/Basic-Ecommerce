package com.devileya.basicecommerce

import android.app.Application
import com.devileya.basicecommerce.domain.module.appModules
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
class BasicEcommerceApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true)
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)

            // LeakCanary
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return
//            }
//            LeakCanary.install(this)

            // Timber
            Timber.plant(Timber.DebugTree())

            //Stetho
            Stetho.initializeWithDefaults(this)
        }


        startKoin {
            androidContext(this@BasicEcommerceApplication)
            modules(appModules)
        }
    }
}