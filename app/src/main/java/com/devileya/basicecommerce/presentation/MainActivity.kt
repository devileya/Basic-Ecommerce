package com.devileya.basicecommerce.presentation

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.devileya.basicecommerce.R
import com.devileya.basicecommerce.base.BaseViewModel.Companion.isLogout
import com.devileya.basicecommerce.base.BaseViewModel.Companion.showError
import com.devileya.basicecommerce.base.BaseViewModel.Companion.showProgress
import com.devileya.basicecommerce.presentation.login.LoginActivity
import com.devileya.basicecommerce.utils.CustomProgress
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        @Suppress("DEPRECATION")
        toolbar?.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP)

        setupNavigationBottom()
        setupSearch()

        val customProgress = CustomProgress(this)

        showProgress.observe(this, Observer { showLoading ->
            if (showLoading!!) customProgress.showLoading() else customProgress.hideLoading()
        })

        showError.removeObservers(this)

        if (!showError.hasActiveObservers()) {
            showError.observe(this, Observer {
                Timber.e("showError $it")
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
        }

        isLogout.observe(this, Observer {
            if (it) {
                isLogout.removeObservers(this)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }

    private fun setupNavigationBottom(){
        val navController = navHostFragment.findNavController()
        bottomNav.setupWithNavController(navController)
        Timber.d("navController ${navController.currentDestination?.navigatorName}")
    }

    private fun setupSearch() {

        val products = arrayOf(
            "Baju Batik","Kemeja","Celana Jeans","Celana Kain","Sepatu",
            "Sneakers","Kacamata"
        )

        val adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_dropdown_item_1line, products
        )

        auto_complete_text_view.setAdapter(adapter)

        auto_complete_text_view.threshold = 1

        auto_complete_text_view.onItemClickListener = AdapterView.OnItemClickListener{
                parent,_,position,_->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }

        btn_fav.setOnClickListener {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.favoriteFragment, true).build()
            Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.goToFavorite, null, navOptions)
        }
    }

    fun showBackButton(visibility: Boolean) {
        when (visibility) {
            true -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                bottomNav.visibility = View.GONE
                btn_fav.visibility = View.GONE
            }
            false -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                bottomNav.visibility = View.VISIBLE
                btn_fav.visibility = View.VISIBLE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        showBackButton(false)
    }
}
