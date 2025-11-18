package org.override.features.map.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import org.override.logistics.core.ui.theme.OverrideLogisticsTheme

@Composable
fun MapRoot(
    viewModel: MapViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MapScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun MapScreen(
    state: MapState,
    onAction: (MapAction) -> Unit,
) {
    val locationService : LocationService = LocationServiceFactory.getOrCreate()
    var locationProvider: DeviceLocationProvider? = null

    val request = LocationProviderRequest.Builder()
        .interval(IntervalSettings.Builder().interval(0L).minimumInterval(0L).maximumInterval(0L).build())
        .displacement(0F)
        .accuracy(AccuracyLevel.HIGHEST)
        .build();

    val result = locationService.getDeviceLocationProvider(request)
    if (result.isValue) {
        locationProvider = result.value!!
    } else {
        Log.e("MapScreen", "Error getting location provider")
    }

    val mapViewportState = rememberMapViewportState()
    MapboxMap(
        modifier = Modifier.fillMaxSize(),
        mapViewportState = mapViewportState,
    ) {
        MapEffect(Unit) { mapView ->
            mapView.location.updateSettings {
                locationPuck = createDefault2DPuck(withBearing = true)
                enabled = true
                puckBearing = PuckBearing.COURSE
                puckBearingEnabled = true
            }
            mapViewportState.transitionToFollowPuckState()
        }

    }
}

@Preview
@Composable
private fun Preview() {
    OverrideLogisticsTheme {
        MapScreen(
            state = MapState(),
            onAction = {}
        )
    }
}