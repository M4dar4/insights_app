package vit.bhopal.insight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import vit.bhopal.insight.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var signBinding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = signBinding.root
        supportActionBar?.title = "Sign up"
        setContentView(view)

        signBinding.SingUp.setOnClickListener {

            val email = signBinding.emailSingUp.text.toString()
            val password = signBinding.passSignUp.text.toString()
            if(email.isNullOrEmpty() || password.isNullOrEmpty())
            {
                Toast.makeText(applicationContext, "Email or password is empty", Toast.LENGTH_SHORT).show()
            }
            else
            {
                signUpFirebase(email,password)
            }

        }

    }

    fun signUpFirebase(email : String, pass : String)
    {
        signBinding.progressBar.isVisible = true
        signBinding.SingUp.isClickable = false
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener { task->
            if(task.isSuccessful)
            {
                val intent = Intent(this@SignUp, VerificationActivity::class.java)
                startActivity(intent)
                finish()
                signBinding.progressBar.isVisible = false
                signBinding.SingUp.isClickable = true
            }
            else
            {
                signBinding.progressBar.isVisible = false
                signBinding.SingUp.isClickable = true
                Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
    }
}