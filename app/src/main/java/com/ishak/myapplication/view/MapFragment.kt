package com.ishak.myapplication.view

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ishak.myapplication.databinding.FragmentMapBinding
import com.ishak.myapplication.databinding.FragmentWhereToBinding
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.navigation.base.extensions.applyDefaultNavigationOptions
import com.mapbox.navigation.base.extensions.applyLanguageAndVoiceUnitOptions
import com.mapbox.navigation.base.route.NavigationRoute
import com.mapbox.navigation.base.route.NavigationRouterCallback
import com.mapbox.navigation.base.route.RouterFailure
import com.mapbox.navigation.base.route.RouterOrigin
import com.mapbox.navigation.core.lifecycle.MapboxNavigationApp
import com.mapbox.navigation.core.trip.session.LocationMatcherResult
import com.mapbox.navigation.core.trip.session.LocationObserver
import com.mapbox.navigation.dropin.map.MapViewObserver
import com.mapbox.navigation.utils.internal.ifNonNull


class MapFragment : Fragment(), OnMapLongClickListener {
    lateinit var binding: FragmentMapBinding

    private var lastLocation: Location? = null


    private val locationObserver = object : LocationObserver {
        override fun onNewLocationMatcherResult(locationMatcherResult: LocationMatcherResult) {
            lastLocation = locationMatcherResult.enhancedLocation
            println( "last location::: $lastLocation")
        }

        override fun onNewRawLocation(rawLocation: Location) {
            // no impl

            println( "lasasdffasd $lastLocation")
        }
    }

    private val mapViewObserver=object: MapViewObserver(){
        override fun onAttached(mapView: MapView) {
            mapView.gestures.addOnMapLongClickListener(
                this@MapFragment
            )

        }

        override fun onDetached(mapView: MapView) {
            mapView.gestures.removeOnMapLongClickListener(
                this@MapFragment
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMapBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight=200
            this.state= BottomSheetBehavior.STATE_COLLAPSED


        }

        binding.navigationView.customizeViewOptions {
            enableMapLongClickIntercept = false
        }

        binding.navigationView.registerMapObserver(mapViewObserver)
        MapboxNavigationApp.current()?.registerLocationObserver(locationObserver)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.navigationView.unregisterMapObserver(mapViewObserver)
        MapboxNavigationApp.current()?.unregisterLocationObserver(locationObserver)
    }

    override fun onMapLongClick(point: Point): Boolean {
        ifNonNull(lastLocation) {
            requestRoutes(Point.fromLngLat(it.longitude, it.latitude), point)
        }
        return false
    }

    private fun requestRoutes(origin: Point, destination: Point) {



        MapboxNavigationApp.current()!!.requestRoutes(
            routeOptions = RouteOptions
                .builder()
                .applyDefaultNavigationOptions()
                .applyLanguageAndVoiceUnitOptions(requireContext())
                .coordinatesList(listOf(origin, destination))
                .alternatives(true)
                .build(),
            callback = object : NavigationRouterCallback {
                override fun onCanceled(routeOptions: RouteOptions, routerOrigin: RouterOrigin) {
                    // no impl
                }

                override fun onFailure(reasons: List<RouterFailure>, routeOptions: RouteOptions) {
                    // no impl
                }

                override fun onRoutesReady(
                    routes: List<NavigationRoute>,
                    routerOrigin: RouterOrigin
                ) {


                    binding.navigationView.api.startRoutePreview(routes)


                    if (binding.navigationView.api.isReplayEnabled()){
                        println("11111111111111111111111")
                    }else{
                        println("22222222222222222222222")
                        binding.navigationView.api.routeReplayEnabled(true)
                    }

                }
            }
        )
    }


}