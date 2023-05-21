package vit.bhopal.insight

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.UserInfo
import vit.bhopal.insight.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var googleClient : GoogleSignInClient
    lateinit var loginBinding: ActivityLoginBinding
    lateinit var activityResult : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        val textGoogle = loginBinding.googleLogin.getChildAt(0) as TextView
        textGoogle.text = "Continue with google"
        textGoogle.setTextColor(Color.BLACK)
        textGoogle.textSize = 18F

        registerAct()

        loginBinding.signInButton.setOnClickListener {
            val email = loginBinding.EmailInput.text.toString()
            val pass = loginBinding.PassInput.text.toString()
            if(email.isNullOrEmpty() || pass.isNullOrEmpty())
            {
                Toast.makeText(applicationContext, "Email or password is empty", Toast.LENGTH_SHORT).show()
            }
            else
            {
                loginFirebase(email,pass)
            }
        }
        loginBinding.googleLogin.setOnClickListener {
            signInGoogle()
        }
        loginBinding.signUpButton.setOnClickListener {
            val intent = Intent(this@LoginActivity,SignUp::class.java)
            startActivity(intent)
        }

    }


    fun loginFirebase(email : String, pass:String)
    {
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener { task->
            if(task.isSuccessful)
            {
                Toast.makeText(applicationContext,"Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(applicationContext,task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        if (user != null) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
        }
    }

    private fun hasPhoneProvider(providerData: List<out UserInfo>): Boolean {
        for (userInfo in providerData) {
            if (userInfo.providerId == PhoneAuthProvider.PROVIDER_ID) {
                return true
            }
        }
        return false
    }

    private fun signInGoogle()
    {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("984171896060-jh0sq5epi5v9rpntd03keuphdm3jtch8.apps.googleusercontent.com")
            .requestEmail().build()

        googleClient = GoogleSignIn.getClient(this,gso)
        signIn()
    }

    private fun registerAct()
    {
        activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback{result->

            val resultCode = result.resultCode
            val data = result.data

            if(resultCode == RESULT_OK && data!=null)
            {
                val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                firebaseGoogleSignIn(task)
            }
        })
    }

    private fun signIn()
    {
        val signIntent : Intent = googleClient.signInIntent
        activityResult.launch(signIntent)

    }

    fun firebaseGoogleSignIn(task:Task<GoogleSignInAccount>)
    {
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            firebaseGoogleAc(account)
            val intent2 = Intent(this@LoginActivity, VerificationActivity::class.java)
            startActivity(intent2)
            finish()
        }
        catch (e: ApiException)
        {
            Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseGoogleAc(account : GoogleSignInAccount)
    {
        val authCred = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(authCred).addOnCompleteListener { task->
            if(task.isSuccessful)
            {/*val user = auth.currentUser */}
            else
            {}
        }
    }
}