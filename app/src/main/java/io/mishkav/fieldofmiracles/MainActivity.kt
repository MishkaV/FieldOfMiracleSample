package io.mishkav.fieldofmiracles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.mishkav.fieldofmiracles.adapter.MainAdapter
import io.mishkav.fieldofmiracles.layoutManager.MainLayoutManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = MainLayoutManager()
        recyclerView.adapter = MainAdapter(list)
    }

    companion object {
        private val list = listOf(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6"
        )
    }
}