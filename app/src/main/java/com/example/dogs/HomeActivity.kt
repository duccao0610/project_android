package com.example.dogs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.support.transition.FragmentTransitionSupport
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.dogs.fragment.BlankFragment


import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


class HomeActivity : AppCompatActivity() {

    var retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    var api = retrofit.create(DogApi::class.java)

    var dogList : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        setUpDrawerLayout()
        getDogTypeList()

        tab_layout.setupWithViewPager(view_pager)
    }

     private fun setUpDrawerLayout() {
        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose)
        drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled
        toggle.syncState()
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }
    }

    fun convertToDogType(response : String){
        try {
            //getting the whole json object from the response
            val obj = JSONObject(response)
            if (obj.optString("status").equals("success")) {

                val dataArray = obj.getJSONObject("message")
                var keys : Iterator<String>  = dataArray.keys()

                while (keys.hasNext()) {
                    val key = keys.next()
                    dogList.add(key)
                }
                Log.d("SIZE","${dogList.size}")

                val adapter = Adapter(supportFragmentManager)
                for (dog in dogList) {
                    Log.d("DOG", dog + "\n")
                    addMenuItem(dog)
                    val f = BlankFragment.newInstance("$dog")
                    adapter.addFragmnet(f,"$dog")
                    getImageList()
                }
                view_pager.adapter = adapter


            } else {
                Log.d("TAG", "Get JSON Failed")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    fun getDogTypeList(){
        var call = api.getDogType()

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i("Responsestring", response.body().toString())
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString())

                        val jsonresponse = response.body().toString()
                        convertToDogType(jsonresponse)

                    } else {
                        Log.i(
                            "onEmptyResponse",
                            "Returned empty response"
                        )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }

    var api1 = retrofit.create(ImageApi::class.java)

    fun convertToImage(response: String){
        try {
            val obj = JSONObject(response)
            if (obj.optString("status").equals("success")){
                val data = obj.getJSONArray("message")
                val list = data.toString()
                Log.d("LINKIMAGE",  list+ "\n")
            }

        }catch (e:JSONException){
            e.printStackTrace()
        }
    }

    fun getImageList(){
        var call = api1.getImage()
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i("Responsestring", response.body().toString())
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString())

                        val jsonresponse = response.body().toString()
                        convertToImage(jsonresponse)
                    } else {
                        Log.i(
                            "onEmptyResponse",
                            "Returned empty response"
                        )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return true
    }

    fun addMenuItem(item:String){
        navigation_view.menu.add(item)
    }

//    fun setUpNavigationView(item:Int){
//        navigation_view.setNavigationItemSelectedListener {
//            when(it.itemId){
//                    supportFragmentManager.beginTransaction().replace(R.id)
//            }
//        }
//    }


}
