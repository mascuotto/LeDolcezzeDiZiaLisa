package com.mascuttoapp.ledolcezzedizialisa

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    lateinit var mAdView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.writeTo), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //ADB
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adMobanner)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment))

    }

    // https://le-dolcezze-di-zia-lisa-default-rtdb.europe-west1.firebasedatabase.app/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_reserved_area -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    override fun onBackPressed() {
        if (findNavController(R.id.nav_host_fragment)?.currentDestination?.id == R.id.FirstFragment)
            finish()
        else {
            onSupportNavigateUp()
            Toast.makeText(this, getString(R.string.backPressedMessage), Toast.LENGTH_LONG).show()
        }
    }
}