package com.example.taskedit

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskedit.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var realm:Realm

    private lateinit var adapter:CustomRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        realm = Realm.getDefaultInstance()

        binding.fab.setOnClickListener { view ->
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        var realmResults = realm.where(TodoList::class.java)
            .findAll()
//            .sort("id", Sort.DESCENDING)

        layoutManager = LinearLayoutManager(this)
        val recyclerView= findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        adapter = CustomRecyclerViewAdapter(realmResults)
        recyclerView.adapter = this.adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper
        .SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {

            override fun onMove(
                RecyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.bindingAdapterPosition
                val toPosition = target.bindingAdapterPosition

//                realm.executeTransaction {
//                    val movingTodo = realmResults[fromPosition]
//                    realmResults.deleteFromRealm(fromPosition)
//                    realmResults.add(toPosition, movingTodo)
//                }

                recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewHolder?.let {
                    recyclerView.adapter?.notifyItemRemoved(viewHolder.bindingAdapterPosition)
//                    realm.executeTransaction {
//                        realmResults.deleteFromRealm()
//                    }
//                    realmResults = realm.where(TodoList::class.java)
//                        .findAll().sort("id", Sort.DESCENDING)
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}