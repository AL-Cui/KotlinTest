package com.example.cuiduo.kotlin.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.cuiduo.kotlin.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    companion object{
        val EMAIL_URI = "mailto:wupanjie0611@gmail.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        collapsing_toolbar.title = getString(R.string.about_name)

        setSupportActionBar(titleBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { email2Me() }

    }

    private fun email2Me() {
        val uri = Uri.parse(EMAIL_URI);
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        startActivity(intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
