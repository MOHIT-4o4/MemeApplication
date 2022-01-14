package com.f1ash.meme

import android.app.Notification
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.f1ash.meme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var APIImageUrl:String? = null
    private lateinit var b: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        LoadMeme()
    }

    fun LoadMeme(){

        b.progressBar.visibility = View.VISIBLE
        APIImageUrl = "https://meme-api.herokuapp.com/gimme"
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, APIImageUrl, null,
            { response ->
                val url = response.getString("url")
                Glide.with(this).load(url).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        b.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        b.progressBar.visibility = View.GONE
                        return false
                    }

                }
                ).into(b.imageView)
            },
            {
            }
        )

        // Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest)
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
    fun ShareMeme(view: android.view.View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.putExtra(Intent.EXTRA_TEXT, "Checkout this MEME...${APIImageUrl}")
        val selector = Intent.createChooser(intent,"Sharing this Using...")
        startActivity(selector)
    }
    fun NextMeme(view: android.view.View) {
        LoadMeme()
    }
}