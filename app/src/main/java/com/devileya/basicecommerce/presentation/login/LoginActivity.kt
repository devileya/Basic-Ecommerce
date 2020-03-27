package com.devileya.basicecommerce.presentation.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.devileya.basicecommerce.BR
import com.devileya.basicecommerce.R
import com.devileya.basicecommerce.databinding.ActivityLoginBinding
import com.devileya.basicecommerce.presentation.MainActivity
import com.devileya.basicecommerce.utils.GeneralEnum
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 777

    private val viewModel: LoginViewModel by viewModel()
    private var callbackManager: CallbackManager? = null
    private val sharedPreferences by inject<SharedPreferences>()

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mProfileTracker: ProfileTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()

        if (sharedPreferences.getBoolean(GeneralEnum.IS_LOGIN.value, false)) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        prepareGoogleSignIn()
        btn_login.setOnClickListener {
            if (!et_username.text.isNullOrEmpty() && !et_password.text.isNullOrEmpty())
                goToMain(GeneralEnum.DEFAULT.value, et_username.text.toString())
            else
                Toast.makeText(this, "Please fill username/password", Toast.LENGTH_SHORT).show()
        }

        btn_login_fb.setOnClickListener { getDataFB() }

        btn_login_google.setSize(SignInButton.SIZE_STANDARD)
        btn_login_google.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun prepareGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun getDataFB() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().logInWithReadPermissions(this, arrayListOf("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Timber.d("loginfb ${loginResult.accessToken} ${Profile.getCurrentProfile()}")
                    var profile = Profile.getCurrentProfile()
                    mProfileTracker = object : ProfileTracker() {
                        override fun onCurrentProfileChanged(
                            oldProfile: Profile,
                            currentProfile: Profile
                        ) {
                            Timber.d("facebook - profile", currentProfile.firstName)
                            profile = currentProfile
                            mProfileTracker.stopTracking()
                        }
                    }

                    goToMain(GeneralEnum.FACEBOOK.value, profile.name, profile.getProfilePictureUri(200,200).toString())
                }

                override fun onCancel() {
                    Timber.d("cancell")
                }

                override fun onError(exception: FacebookException) {
                    Timber.d("erorrr ${exception.message}")
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)?.also {
                    goToMain(GeneralEnum.GOOGLE.value, it.displayName, it.photoUrl.toString())
                }
            } catch (e: ApiException) {
                Timber.d("error google sign in ${e.message}")
            }
        } else callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    private fun goToMain(login: String, name: String?, photo: String? = null) {
        sharedPreferences.edit()
            .putBoolean(GeneralEnum.IS_LOGIN.value, true)
            .putString(GeneralEnum.LOGIN.value, login)
            .putString(GeneralEnum.NAME.value, name)
            .putString(GeneralEnum.PHOTO.value, photo)
            .apply()
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}
