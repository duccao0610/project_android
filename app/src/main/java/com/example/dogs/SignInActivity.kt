package com.example.dogs

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_sign_in.*
class SignInActivity : AppCompatActivity(),GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d("duc","onConnectionFailed" + connectionResult)
    }

    private val RC_SIGN_IN = 9001
    private var mGoogleApiClient : GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        btn_signin.setOnClickListener {
            emailValidation()
        }

        updateUI(false)

        val gso  = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
            .build()

        btn_gg.setOnClickListener(View.OnClickListener {
            var signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent,RC_SIGN_IN)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            updateUI(result.isSuccess)
        }
    }

    private fun updateUI(isLogIn: Boolean) {
        if(isLogIn){
            Toast.makeText(applicationContext,"Logged In", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
        else{
            btn_gg.visibility = View.VISIBLE
        }
    }

    fun emailValidation(){
        var email : String = edit1.getText().toString().trim()
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            passValidation()
        }
        else{
            Toast.makeText(applicationContext,"Invalid email address", Toast.LENGTH_SHORT).show()
        }
    }
    fun passValidation(){
        var pass : String  = edit2.getText().toString().trim()
        if(pass.length <=6){
            Toast.makeText(applicationContext,"Password format not match", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext,"Logged In", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
