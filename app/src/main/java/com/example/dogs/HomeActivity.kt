package com.example.dogs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.example.dogs.fragment.BlankFragment
import com.squareup.picasso.Picasso


import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.fragment_blank.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory







class HomeActivity : AppCompatActivity()  {

    var retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    var api = retrofit.create(DogApi::class.java)

    var dogList : ArrayList<String> = ArrayList()

    var linkGG:String = "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwickZq60NvjAhWbad4KHTBLCs0QjRx6BAgBEAU&url=https%3A%2F%2Fwww.youtube.com%2FGoogle&psig=AOvVaw1gO2Dd7qUvhJXw44NnQkTZ&ust=1564541241070337"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        setUpDrawerLayout()
        handleUserInfo()
        getDogTypeList()
        handleNavView()
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
                val keys : Iterator<String>  = dataArray.keys()

                while (keys.hasNext()) {
                    val key = keys.next()
                    dogList.add(key)
                }
                Log.d("SIZE","${dogList.size}")

                val adapter = Adapter(supportFragmentManager)
//                for (dog in dogList) {
////                    Log.d("DOG", dog + "\n")
////                    addMenuItem(dog)
////                    val f = BlankFragment.newInstance("$dog","$linkGG")
////                    adapter.addFragmnet(f,"$dog")
////                    getImageList()
////                }
                for (dog1 in dogList.indexOf(dogList[0])  until dogList.indexOf(dogList[5])) {
                    Log.d("dog1", dogList[dog1] + "\n")
                    addMenuItem(dogList[dog1],dog1)
                    val f = BlankFragment.newInstance(dogList[dog1], linkGG)
                    adapter.addFragmnet(f, dogList[dog1])
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
        val call = api.getDogType()

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

    var urlList : ArrayList<String> = ArrayList()
    fun convertToImage(response: String){
        try {
            val obj = JSONObject(response)
            if (obj.optString("status").equals("success")){
                val data = obj.getJSONArray("message")
                var i = 0
//                var imgUrlList : ArrayList<String> = ArrayList()
                val count = data.length()
                while (i < count) {
                    try {
                        urlList.add(data[i].toString())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    i++
                }
                Picasso.with(getApplicationContext()).load(urlList[0]).into(image_test)
                Log.d("SIZE","${urlList.size}")
                Log.d("LINK1",urlList.random())
            }

        }catch (e:JSONException){
            e.printStackTrace()
        }
    }

    fun getImageList(){
        val call = api1.getImage()
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

    fun addMenuItem(item:String,id:Int){
        navigation_view.menu.add(1,id,id,item)
        val idea = navigation_view.menu.getItem(id).itemId
        Log.d("ID","$idea")

    }

    fun handleUserInfo(){
        val strUser = intent.getStringExtra("Name")
        val strEmail = intent.getStringExtra("Email")
        val strAvatarUrl = intent.getStringExtra("Avatar")
        GG_name.setText(strUser)
        GG_email.setText(strEmail)
        Picasso.with(getApplicationContext()).load(strAvatarUrl).into(avatar_image)

    }

    fun handleNavView(){
        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                0-> {
                    // handle click
                    Toast.makeText(applicationContext,"Item0 selected",Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                1-> {
                    // handle click
                    Toast.makeText(applicationContext,"Item1 selected",Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                2-> {
                    // handle click
                    Toast.makeText(applicationContext,"Item2 selected",Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                3-> {
                    // handle click
                    Toast.makeText(applicationContext,"Item3 selected",Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                4-> {
                    // handle click
                    Toast.makeText(applicationContext,"Item4 selected",Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> false
            }
        }

    }




}
