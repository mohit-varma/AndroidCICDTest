package com.example.androidcicdtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.androidcicdtest.interfaces.QuotesApi
import kotlinx.coroutines.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var textViewNameId : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
        initViews()

        var quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
        GlobalScope.launch(Dispatchers.IO) {
           val quotesList =  quotesApi.getQuotes(page = 107)
            withContext(Dispatchers.Main) {
                textViewNameId.text = quotesList.body().toString()
                quotesList.body()?.let {
                    Log.d(MainActivity::class.java.simpleName, "onCreate: size = ${it.results.size}")
                }
            }
        }
    }

    private fun initViews() {
        textViewNameId = findViewById(R.id.textViewNameId)
    }
}