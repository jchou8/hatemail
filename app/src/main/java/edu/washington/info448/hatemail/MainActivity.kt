package edu.washington.info448.hatemail

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.io.IOException
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.collections.ArrayList
import android.R.id.edit
import android.content.Intent
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {
    var histories = arrayListOf(his1, his2, his3)
    var schedules = arrayListOf(sche1, sche2, sche3)
    lateinit var myHistories: ArrayList<History>
    lateinit var mySchedules: ArrayList<Schedule>


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("hahahahahaha", HateMailApp.getSingletonInstance().isNightModeEnabled().toString())
        if (HateMailApp.getSingletonInstance().isNightModeEnabled() == true) {
            setTheme(R.style.dark)
        } else {
            setTheme(R.style.light)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set up share preference
        val appSharedPrefs = this.getSharedPreferences("prefs", 0)
        val prefsEditor = appSharedPrefs.edit()
        val gson = Gson()
        /////
        val dummyHistory = gson.toJson(histories)
        prefsEditor.putString("Histories", dummyHistory)
        prefsEditor.commit()
        val dummySchedule = gson.toJson(schedules)
        prefsEditor.putString("Schedules", dummySchedule)
        prefsEditor.commit()
        /////
        val json = appSharedPrefs.getString("Histories", "")
        val type = object : TypeToken<List<History>>() {
        }.type

        val json2 = appSharedPrefs.getString("Schedules", "")
        val type2 = object : TypeToken<List<Schedule>>() {
        }.type

        this.myHistories = gson.fromJson(json, type)
        this.mySchedules = gson.fromJson(json2, type2)



        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        fragmentAdapter.myHistories = this.myHistories
        fragmentAdapter.mySchedules = this.mySchedules
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflatar = menuInflater
        inflatar.inflate(R.menu.cos_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        val settingFragment = SettingFragment()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, settingFragment, "SETTING_FRAGMENT")
//            .commit()
        val intent = Intent(this, PreferencesActivity::class.java)

        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}
