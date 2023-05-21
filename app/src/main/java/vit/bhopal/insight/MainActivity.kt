package vit.bhopal.insight

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import vit.bhopal.insight.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        loginBinding.profileImage.setOnClickListener {
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://linktr.ee/insights_club"))
            startActivity(gourl)
        }
        loginBinding.discord.setOnClickListener {
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.com/invite/TDkpXKGpgz"))
            startActivity(gourl)
        }
        loginBinding.insta.setOnClickListener {
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/insights_club/"))
            startActivity(gourl)
        }
        loginBinding.facebook.setOnClickListener {
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/insightsclubvitb/"))
            startActivity(gourl)
        }
        loginBinding.youtube.setOnClickListener {
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC9JuWZdup3Lkh05KObjtptw"))
            startActivity(gourl)
        }
        loginBinding.recruit.setOnClickListener {
            val animationZoomOut = AnimationUtils.loadAnimation(this, R.anim.bounce)
            loginBinding.recruit.startAnimation(animationZoomOut)
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSfPSa0-lbu6p7iqHRAS6OgTva1RDGJGz4XDopYTxkVtMWiWNA/closedform"))
            startActivity(gourl)
        }
        loginBinding.campustour.setOnClickListener {
            val animationZoomOut = AnimationUtils.loadAnimation(this, R.anim.bounce)
            loginBinding.campustour.startAnimation(animationZoomOut)
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/playlist?list=PLJ3xleni50O62csO_8Ze86yfntfwuLP2o"))
            startActivity(gourl)
        }
        loginBinding.Issue.setOnClickListener {
            val animationZoomOut = AnimationUtils.loadAnimation(this, R.anim.bounce)
            loginBinding.Issue.startAnimation(animationZoomOut)
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1ebhoDAgpxwo-IPqQzuYyRwDUfeONUhCH/view"))
            startActivity(gourl)
        }
        loginBinding.publications.setOnClickListener {
            val animationZoomOut = AnimationUtils.loadAnimation(this, R.anim.bounce)
            loginBinding.publications.startAnimation(animationZoomOut)
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1A9baopK-DFdGUhRcMYkZHNmFYlD3uLJl"))
            startActivity(gourl)
        }
        loginBinding.event.setOnClickListener {
            val animationZoomOut = AnimationUtils.loadAnimation(this, R.anim.bounce)
            loginBinding.event.startAnimation(animationZoomOut)
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/u/1/d/e/1FAIpQLSd3gD6qc3N5bVA5GwYi4OSGhKK5CNYhSuBlEhXSkpp3ZCm7FQ/viewform?usp=send_form"))
            startActivity(gourl)
        }
        loginBinding.inquistadors.setOnClickListener {
            val animationZoomOut = AnimationUtils.loadAnimation(this, R.anim.bounce)
            loginBinding.inquistadors.startAnimation(animationZoomOut)
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://tinyurl.com/bddwas8m"))
            startActivity(gourl)
        }
        loginBinding.igniters.setOnClickListener {
            val animationZoomOut = AnimationUtils.loadAnimation(this, R.anim.bounce)
            loginBinding.igniters.startAnimation(animationZoomOut)
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/playlist?list=PLJ3xleni50O5gMJ7du_tE1ZFhvwGQaPXJ"))
            startActivity(gourl)
        }
        loginBinding.whatsapp.setOnClickListener {
            val animationZoomOut = AnimationUtils.loadAnimation(this, R.anim.bounce)
            loginBinding.whatsapp.startAnimation(animationZoomOut)
            var gourl= Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.whatsapp.com/C62IvwZ7RX0JE3sZJB66oQ"))
            startActivity(gourl)
        }
        loginBinding.buttonLogout.setOnClickListener {
            logout()
        }

    }
    private fun logout() {
        firebaseAuth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}