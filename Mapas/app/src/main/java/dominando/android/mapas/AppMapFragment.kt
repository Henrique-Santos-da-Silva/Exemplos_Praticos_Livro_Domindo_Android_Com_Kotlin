package dominando.android.mapas

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class AppMapFragment : SupportMapFragment() {
//    private val viewModel: MapViewModel by lazy {
//        ViewModelProviders.of(requireActivity()).get(MapViewModel::class.java)
////        ViewModelProvider(this).get(MapViewModel::class.java)
//    }

    private val viewModel: MapViewModel by viewModels({requireActivity()})

    private var markerCurrentLocation: Marker? = null


    private var googleMap: GoogleMap? = null

    override fun getMapAsync(callback: OnMapReadyCallback?) {
        super.getMapAsync {
            googleMap = it
            setupMap()
            callback?.onMapReady(googleMap)
        }
    }

    private fun setupMap() {
        googleMap?.run {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            uiSettings.isMapToolbarEnabled = false
            uiSettings.isZoomControlsEnabled = true

            setOnMapLongClickListener { latLng ->
                onMapLongClick(latLng)
            }
        }

        viewModel.getMapState()
            .observe(this, Observer { mapState ->
                if (mapState != null) {
                    updateMap(mapState)
                }
            })

        viewModel.getCurrentLocation()
            .observe(this, Observer { currentLocation ->
                if (currentLocation != null) {
                    if (markerCurrentLocation == null) {
                        val icon = BitmapDescriptorFactory.fromResource(R.drawable.blue_marker)
                        markerCurrentLocation = googleMap?.addMarker(MarkerOptions()
                            .title(getString(R.string.map_current_location))
                            .icon(icon)
                            .position(currentLocation)
                        )
                    }
                    markerCurrentLocation?.position = currentLocation
                }
            })
    }

    private fun updateMap(mapState: MapViewModel.MapState) {
        googleMap?.run {
            clear()
            val area = LatLngBounds.builder()
            val origin = mapState.origin
            val route = mapState.route
            markerCurrentLocation = null

            if (route != null && route.isNotEmpty()) {
                val polylineOptions = PolylineOptions()
                    .addAll(route)
                    .width(5f)
                    .color(Color.RED)
                    .visible(true)
                addPolyline(polylineOptions)
                route.forEach { area.include(it) }
            }


            if (origin != null) {
                addMarker(
                    MarkerOptions()
                        .position(origin)
                        .title(getString(R.string.map_marker_origin))
                )
                area.include(origin)

            }

            val destination = mapState.destination
            if (destination != null) {
                addMarker(
                    MarkerOptions()
                        .position(destination)
                        .title(getString(R.string.map_marker_destination)))
                area.include(destination)
            }
            if (origin != null) {
                if (destination != null) {
                    animateCamera(CameraUpdateFactory.newLatLngBounds(area.build(), 50))
                } else {
                    animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 17f))
                }
            }

            val geofenceInfo = mapState.geofenceInfo
            if (geofenceInfo != null) {
                val latLng = LatLng(geofenceInfo.latitude, geofenceInfo.longitude)
                addCircle(CircleOptions()
                    .strokeWidth(2f)
                    .fillColor(0x990000ff.toInt())
                    .center(latLng)
                    .radius(geofenceInfo.radius.toDouble())
                )
            }
        }
    }

    private fun onMapLongClick(latLng: LatLng) {
        val pit = PendingIntent.getBroadcast(requireContext(), 0, Intent(requireContext(), GeofenceReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        viewModel.setGeofence(pit, latLng)
    }
}