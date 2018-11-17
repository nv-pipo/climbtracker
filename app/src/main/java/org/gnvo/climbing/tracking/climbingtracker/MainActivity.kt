package org.gnvo.climbing.tracking.climbingtracker

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.gnvo.climbing.tracking.climbingtracker.data.room.pojo.ClimbEntry
import org.gnvo.climbing.tracking.climbingtracker.data.room.pojo.Pitch
import org.gnvo.climbing.tracking.climbingtracker.data.room.pojo.RouteGrade
import org.gnvo.climbing.tracking.climbingtracker.ui.main.AddEditEntryActivity
import org.gnvo.climbing.tracking.climbingtracker.ui.main.MainViewModel
import org.gnvo.climbing.tracking.climbingtracker.ui.main.EntryAdapter
import java.util.*

class MainActivity : AppCompatActivity() {

    val ADD_CLIMBING_ENTRY_REQUEST: Int = 1
    val EDIT_CLIMBING_ENTRY_REQUEST: Int = 2

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button_add_climb_entry.setOnClickListener {
            Log.d("gnvo", "CLICK")
            viewModel.insertClimbEntry(
                ClimbEntry(
//                    name = "route name2",
//                    coordinates = "2.0,2.0",
//                    site = "site2",
//                    sector = "sector2",
                    datetime = Date(System.currentTimeMillis()),
                    pitches = listOf(
                        Pitch(
                            pitchNumber = 2,
                            routeGrade = RouteGrade(id = 1)
                        ),
                        Pitch(
                            pitchNumber = 1,
                            routeGrade = RouteGrade(id = 2)
                        )
                    ),
                    routeType = "Sport"
//                    rating = 2,
//                    comment = "comments21"
                )
            )

//            viewModel.getAllRouteTypeNames().observe(this@MainActivity, Observer { routeTypeNames: List<String>? ->
//                routeTypeNames?.forEach {
//                    Log.d("gnvo", it)
//
//                }
//            })

//            val intent = Intent(this@MainActivity, AddEditEntryActivity::class.java)
//            startActivityForResult(intent, ADD_CLIMBING_ENTRY_REQUEST)
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val adapter = EntryAdapter()
        recycler_view.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getAllClimbingEntries().observe(this, Observer {
            adapter.submitList(it!!)
        })

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteClimbEntry(adapter.getClimbingEntryAt(position))
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : EntryAdapter.OnItemClickListener {
            override fun onItemClick(climbEntry: ClimbEntry) {
                val intent = Intent(this@MainActivity, AddEditEntryActivity::class.java)
                intent.putExtra(AddEditEntryActivity.EXTRA_ID, climbEntry.id)
                intent.putExtra(AddEditEntryActivity.EXTRA_ROUTE_NAME, climbEntry.name)
                intent.putExtra(AddEditEntryActivity.EXTRA_COMMENT, climbEntry.comment)
//                intent.putExtra(AddEditEntryActivity.EXTRA_PRIORITY, climbEntry.priority )

                startActivityForResult(intent, EDIT_CLIMBING_ENTRY_REQUEST)
            }
        })

        viewModel.getAllClimbingEntries().observe(this, Observer { climbEntries: List<ClimbEntry>? ->
            Log.d("gnvo", climbEntries.toString())
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "ClimbEntry not saved", Toast.LENGTH_LONG).show()
            return
        }

        val routeName = data?.getStringExtra(AddEditEntryActivity.EXTRA_ROUTE_NAME)
        val description = data?.getStringExtra(AddEditEntryActivity.EXTRA_COMMENT)
        val priority = data?.getIntExtra(AddEditEntryActivity.EXTRA_PRIORITY, -1)
        val id = data?.getIntExtra(AddEditEntryActivity.EXTRA_ID, AddEditEntryActivity.INVALID_ID)

        when (requestCode) {
            ADD_CLIMBING_ENTRY_REQUEST -> {
//                val climbEntry = ClimbEntry(name = title!!, comments = description!!, priority = priority)
//                viewModel.insertClimbEntry(climbEntry)

                Toast.makeText(this, "ClimbEntry created", Toast.LENGTH_LONG).show()
            }
            EDIT_CLIMBING_ENTRY_REQUEST -> {
                if (id == AddEditEntryActivity.INVALID_ID) {
                    Toast.makeText(this, "ClimbEntry could not be updated", Toast.LENGTH_LONG).show()
                    return
                }

//                val climbEntry = ClimbEntry(name = title!!, comments = description!!, priority = priority)
//                climbEntry.id = id
//                viewModel.updateClimbEntry(climbEntry)

                Toast.makeText(this, "ClimbEntry updated", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_climb_entries -> {
                viewModel.deleteAllClimbingEntries()
                Toast.makeText(this, "All climb entries deleted", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}