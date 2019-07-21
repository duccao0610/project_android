package com.example.dogs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log



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
        initView()
        getDogTypeList()
    }

    private fun initView() {
        view_pager.adapter = MyAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
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
//                val intent = Intent(applicationContext,MyAdapter::class.java)
//                    .putStringArrayListExtra("myList",dogList)
//                startActivity(intent)


                for (dog in dogList) {
                    Log.d("DOG", dog + "\n")
                }

//                val arrayAdapter = ArrayAdapter<String>(
//                    this,
//                    android.R.layout.simple_list_item_1,
//                    dogList
//                )
//                list_view.setAdapter(arrayAdapter)



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


}
