package com.minamagid.mazaady

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.minamagid.mazaady.domain.model.general.LocalSelectedModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private val mList = ArrayList<LocalSelectedModel?>()
        @JvmStatic
        fun clearGeneral(){
            return mList.clear()
        }
        @JvmStatic
        fun addGeneral(model: LocalSelectedModel) {
            if (model !in mList) {
                mList.add(model)
            }
        }

        @JvmStatic
        fun deleteGeneral(model: LocalSelectedModel) {
            if (model !in mList) {
                mList.remove(model)
            }
        }

        @JvmStatic
        fun getAllGeneral():ArrayList<LocalSelectedModel?> {
            return mList
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_main_fragment)

        navView.setupWithNavController(navController)
    }

}