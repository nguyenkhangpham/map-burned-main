package com.canhbbaochayrung

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.canhbbaochayrung.databinding.ActivityMainBinding
import com.canhbbaochayrung.utils.State
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.heatmaps.HeatmapTileProvider
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class Main2Activity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding
    private val calendar = Calendar.getInstance()
    private var length = 1
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fireLiveData.observe(this) { state ->
            when (state) {
                is State.Failed -> {
                    binding.progressBar.visibility = View.GONE

                }

                is State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                }

                is State.Success -> {
                    binding.progressBar.visibility = View.GONE
                    displayHeat(state.data)
                }
            }
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.tvDate.setOnClickListener {
            pickDate()
        }
        binding.tvDateLength.setOnClickListener {
            pickLength()
        }
        updateData()
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
    }

    private fun displayHeat(items: List<FirePoint>) {
        if (items.isEmpty()) return
        if (!this::mMap.isInitialized) return
        val latLngs = items.map { LatLng(it.lat, it.lng) }
        val builder = HeatmapTileProvider.Builder()
            .data(latLngs)
            .build()
        val overlay =
            mMap.addTileOverlay(TileOverlayOptions().tileProvider(builder))
        val bounds = LatLngBounds.Builder().let { b ->
            latLngs.forEach { p -> b.include(p) }
            b.build()
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15))
    }

    private fun updateData() {
        binding.tvDate.text = "Date : ${calendar.time.toString("yyyy-MM-dd")}"
        binding.tvDateLength.text = "Length : $length"
        viewModel.loadDataFromNasa()
    }

    private fun pickDate() {
        val dialog = DatePickerDialog(this)
        dialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            viewModel.changeDate(calendar.time)
            updateData()

        }

        dialog.show()
    }

    private fun pickLength() {
        val values = List(10) { it + 1 }
        val adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, values)
        AlertDialog.Builder(this)
            .setTitle("Pick length")
            .setAdapter(adapter) { dialog, which ->
                dialog.dismiss()
                Timber.d(">> $which")
                viewModel.range = which + 1
                length = which + 1
                updateData()
            }
            .create()
            .show()
    }
}