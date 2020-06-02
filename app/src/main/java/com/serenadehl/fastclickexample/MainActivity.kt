package com.serenadehl.fastclickexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.serenadehl.fastclick.FastClick
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener(object :View.OnClickListener{
            @FastClick
            override fun onClick(v: View?) {
                Log.e("=============>","${this}")
            }
        })
    }
}
