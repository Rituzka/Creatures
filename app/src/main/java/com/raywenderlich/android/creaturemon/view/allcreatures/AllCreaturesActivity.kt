package com.raywenderlich.android.creaturemon.view.allcreatures

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.raywenderlich.android.creaturemon.R
import com.raywenderlich.android.creaturemon.view.creature.CreatureActivity
import com.raywenderlich.android.creaturemon.viewModel.AllCreaturesViewModel
import kotlinx.android.synthetic.main.activity_all_creatures.*
import kotlinx.android.synthetic.main.content_all_creatures.*

class AllCreaturesActivity : AppCompatActivity() {

  private val adapter = CreatureAdapter(mutableListOf())
  private lateinit var viewModel: AllCreaturesViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_all_creatures)
    setSupportActionBar(toolbar)

    viewModel = ViewModelProviders.of(this).get(AllCreaturesViewModel::class.java)

    creaturesRecyclerView.layoutManager = LinearLayoutManager(this)
    creaturesRecyclerView.adapter = adapter

    viewModel.getAllCreaturesLiveData().observe(this, Observer {creatures ->
      creatures?.let {
        adapter.updateCreatures(creatures)
      }

    })

    fab.setOnClickListener {
      startActivity(Intent(this, CreatureActivity::class.java))
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_clear_all -> {
        viewModel.clearAllCreatures()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
