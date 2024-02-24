package com.ushastoe.lampcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    private var url = "http://10.0.0.50"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        findViewById<Switch>(R.id.swlamp).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                enableLight(url)
            } else {
                disableLight(url)
            }
        }
        runBlocking {
            fetchData(url)
        }
    }

    override fun onResume() {
        super.onResume()
        runBlocking {
            fetchData(url)
        }
    }

    private fun enableLight(BASE_URL: String) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, "$url/on",
            {
                Toast.makeText(applicationContext, "Запрос отправлен", Toast.LENGTH_SHORT).show()
            },
            { Toast.makeText(applicationContext, "Что-то не сработало...", Toast.LENGTH_SHORT).show() })
        queue.add(stringRequest)
    }

    private fun disableLight(BASE_URL: String) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, "$url/off",
            {
                Toast.makeText(applicationContext, "Запрос отправлен", Toast.LENGTH_SHORT).show()
            },
            { Toast.makeText(applicationContext, "Что-то не сработало...", Toast.LENGTH_SHORT).show() })
        queue.add(stringRequest)
    }

    private fun fetchData(BASE_URL: String) {
        val textView = findViewById<TextView>(R.id.status)
        textView.text = "Подключаюсь..."
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, BASE_URL,
            {
                textView.text = "✅ Лампа доступна"
            },
            { textView.text = "❌ Лампа не доступна"})
        queue.add(stringRequest)
    }
}