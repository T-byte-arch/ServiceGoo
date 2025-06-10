package com.talitamorales.servicegoo.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.talitamorales.servicegoo.R
import com.talitamorales.servicegoo.models.LocalService

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.fragmentView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val selectedService = arguments?.getString("Selected service")
        selectedService?.let {
            Toast.makeText(requireContext(), "Service Selected: $it", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getDeviceLocation()
    }

    private fun getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            map.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val userLocation = LatLng(it.latitude, it.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                    map.addMarker(MarkerOptions().position(userLocation).title("Your Location"))
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceLocation()
        }
    }

    private fun showServiceOnMap() {
        val listServices = listOf(
            LocalService("Salão da Ana", "$50.00",LatLng(-23.5631, -46.6544)),
            LocalService("Beleza Express", "$50.00",LatLng(-23.5610, -46.6550)),
            LocalService("Top Hair", "$50.00", LatLng(-23.5640, -46.6525)),
            LocalService("Top Hair", "$50.00", LatLng(-23.5640, -46.6525)),
            LocalService("Top Hair", "$50.00", LatLng(-23.5640, -46.6525)),
            LocalService("Top Hair", "$50.00", LatLng(-23.5640, -46.6525)),
            LocalService("Top Hair", "$50.00", LatLng(-23.5640, -46.6525)),
            LocalService("Top Hair", "$50.00", LatLng(-23.5640, -46.6525)),
            LocalService("Top Hair", "$50.00", LatLng(-23.5640, -46.6525)),
            LocalService("Top Hair", "$50.00", LatLng(-23.5640, -46.6525))
        )

        for (service in listServices) {
            map.addMarker(
                MarkerOptions()
                    .position(service.latLng)
                    .title("${service.name}")
            )
        }
    }
}
