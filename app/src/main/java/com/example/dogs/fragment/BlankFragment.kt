package com.example.dogs.fragment

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.dogs.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_blank.view.*



class BlankFragment : Fragment() {
    var id = ""
    var text = ""
    var link = ""
    companion object{
        fun newInstance(text: String,link:String):BlankFragment{
            val fragment = BlankFragment()
            val bundle = Bundle()
            bundle.putString("Text",text)
            bundle.putString("Link",link)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        text = arguments?.get("Text").toString()
        link = arguments?.get("Link").toString()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        Picasso.with(view?.context).load(link).into(image_test)
        return inflater.inflate(R.layout.fragment_blank,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.text_link.setText(link)
        val texxt = text.toUpperCase()
        view.text_blank.setText(texxt)

//        view.image_test.setImageResource(R.drawable._search_white)
//        Picasso.with(view.context).load(link).into(image_test)
    }
}
