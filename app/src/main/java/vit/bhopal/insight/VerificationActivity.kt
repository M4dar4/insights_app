package vit.bhopal.insight

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import vit.bhopal.insight.databinding.ActivityVerificationBinding
import java.util.concurrent.TimeUnit


class VerificationActivity : AppCompatActivity() {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var verifyBinding : ActivityVerificationBinding

    private var verificationId : String? = null
    private var edtPhone: String? = null
    private  var edtOTP:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyBinding = ActivityVerificationBinding.inflate(layoutInflater)
        val view = verifyBinding.root
        setContentView(view)

        verifyBinding.GetOTP.setOnClickListener {
            edtPhone = verifyBinding.editTextPhone.text.toString()

            sendVerificationCode("+91" + edtPhone)
            verifyBinding.EnterOTP.isVisible = true
            verifyBinding.verifyOTP.isVisible = true
        }

        verifyBinding.verifyOTP.setOnClickListener {
            val otp = verifyBinding.EnterOTP.text.toString()
            if(otp.isNullOrEmpty())
            {
                Toast.makeText(applicationContext,"Enter OTP", Toast.LENGTH_SHORT).show()
            }
            else
            {
                verifyCode(otp)
            }
        }
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext,"Welcome to Insights VITB",Toast.LENGTH_SHORT).show()
                    val i = Intent(this@VerificationActivity, MainActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, task.exception!!.message, Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun sendVerificationCode(phoneNum : String)
    {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNum)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val
            callbacks: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                verificationId = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    edtOTP?.setText(code)
                    verifyCode(code)
                }
            }
            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }
}