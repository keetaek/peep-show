package com.kakaw.peepshow.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.kakaw.peepshow.R;
import com.kakaw.peepshow.dao.dto.DropInfoDTO;
import com.kakaw.peepshow.helper.Constants;
import com.kakaw.peepshow.helper.SharedPreferenceHelper;
import com.kakaw.peepshow.manager.UserActivitySummaryManager;
import com.kakaw.peepshow.map.PeepshowItemizedOverlay;
import com.kakaw.peepshow.map.PeepshowOverlayItem;
import com.squareup.otto.Subscribe;

import org.osmdroid.ResourceProxy;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by keetaekhong on 11/16/14.
 */
public class DropInfoMapFragment extends BaseFragment {

    @Inject
    UserActivitySummaryManager userActivitySummaryManager;
    private ResourceProxy mResourceProxy;
    private MapView mMapView;
    private MyLocationNewOverlay mLocationOverlay;
    private CompassOverlay mCompassOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private SharedPreferences mPrefs;
    private ItemizedOverlay<OverlayItem> mMyLocationOverlay;

    public static DropInfoMapFragment newInstance() {
        DropInfoMapFragment fragment = new DropInfoMapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBus.register(this);

        if (mPrefs.getBoolean(Constants.Map.PREFS_SHOW_LOCATION, false)) {
            this.mLocationOverlay.enableMyLocation();
        }
        if (mPrefs.getBoolean(Constants.Map.PREFS_SHOW_COMPASS, false)) {
            this.mCompassOverlay.enableCompass();
        }

    }

    @Override
    public void onPause() {

        mBus.unregister(this);

        final SharedPreferences.Editor edit = mPrefs.edit();

        edit.putInt(Constants.Map.PREFS_SCROLL_X, mMapView.getScrollX());
        edit.putInt(Constants.Map.PREFS_SCROLL_Y, mMapView.getScrollY());
        edit.putInt(Constants.Map.PREFS_ZOOM_LEVEL, mMapView.getZoomLevel());
        edit.putBoolean(Constants.Map.PREFS_SHOW_LOCATION, mLocationOverlay.isMyLocationEnabled());
        edit.putBoolean(Constants.Map.PREFS_SHOW_COMPASS, mCompassOverlay.isCompassEnabled());
        edit.commit();

        this.mLocationOverlay.disableMyLocation();
        this.mCompassOverlay.disableCompass();

        super.onPause();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.drop_info_map_fragment, container, false);
//        return rootView;
        mResourceProxy = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
        mMapView = new MapView(inflater.getContext(), 256, mResourceProxy);
        // Call this method to turn off hardware acceleration at the View level.
        // setHardwareAccelerationOff();

        return mMapView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.drop_info_map_fragment_actions, menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Context context = this.getActivity();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        // mResourceProxy = new ResourceProxyImpl(getActivity().getApplicationContext());

        mPrefs = SharedPreferenceHelper.getSharedPreferences(context);

        this.mCompassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context),
                mMapView);
        this.mLocationOverlay = new MyLocationNewOverlay(context, new GpsMyLocationProvider(context),
                mMapView);

        mMinimapOverlay = new MinimapOverlay(context, mMapView.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);

        mScaleBarOverlay = new ScaleBarOverlay(context);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);

        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapView.getOverlays().add(this.mLocationOverlay);
        mMapView.getOverlays().add(this.mCompassOverlay);
        mMapView.getOverlays().add(this.mMinimapOverlay);
        mMapView.getOverlays().add(this.mScaleBarOverlay);

//        mMapView.getController().setZoom(mPrefs.getInt(Constants.Map.PREFS_ZOOM_LEVEL, 1));
//        mMapView.scrollTo(mPrefs.getInt(Constants.Map.PREFS_SCROLL_X, 0), mPrefs.getInt(Constants.Map.PREFS_SCROLL_Y, 0));

        mLocationOverlay.enableMyLocation();
        mCompassOverlay.enableCompass();

        			/* Create a static ItemizedOverlay showing a some Markers on some cities. */
        final ArrayList<PeepshowOverlayItem> items = new ArrayList<PeepshowOverlayItem>();

        items.add(new PeepshowOverlayItem("CentralPark", "Central Park",
                "Central Park in New York City", new GeoPoint(40.7820, -73.9660), null,
                OverlayItem.HotspotPlace.BOTTOM_CENTER));

        items.add(new PeepshowOverlayItem("NorthCentralPark", "North Central Park",
                "North of Central Park in New York City", new GeoPoint(41.7820, -73.9660),
                mResourceProxy.getDrawable(ResourceProxy.bitmap.marker_default), OverlayItem.HotspotPlace.CENTER));

        GeoPoint myHomeGeoPt = new GeoPoint(47.649751, -122.346134);
//        items.add(new PeepshowOverlayItem("MyHome", "My Home",
//                "My home in Fremont", myHomeGeoPt,
//                mResourceProxy.getDrawable(ResourceProxy.bitmap.marker_default), OverlayItem.HotspotPlace.CENTER));

        items.add(new PeepshowOverlayItem("Milstead", "Milstead",
                "My favorite coffee shop", new GeoPoint(47.649431, -122.347757),
                mResourceProxy.getDrawable(ResourceProxy.bitmap.marker_default), OverlayItem.HotspotPlace.TOP_CENTER));

        // Setting the zoom level and fixing the camera to my home location.
        mMapView.getController().setZoom(mPrefs.getInt(Constants.Map.PREFS_ZOOM_LEVEL, 10));
        mMapView.getController().setCenter(myHomeGeoPt);

        Marker startMarker = new Marker(mMapView);
        startMarker.setPosition(myHomeGeoPt);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
        startMarker.setTitle("Start point");
        startMarker.setSnippet("Description of a kind?");
        startMarker.setSubDescription("Sub description");
        Drawable icon = getResources().getDrawable(R.drawable.ic_continue);
        startMarker.setImage(icon);
        mMapView.getOverlays().add(startMarker);

        			/* OnTapListener for the Markers, shows a simple Toast. */
        this.mMyLocationOverlay = new PeepshowItemizedOverlay(items,
                new ItemizedIconOverlay.OnItemGestureListener<PeepshowOverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, PeepshowOverlayItem item) {
                        Toast.makeText(
                                DropInfoMapFragment.this.getActivity(),
                                "Item '" + item.getTitle() + "' (index=" + index
                                        + ") got single tapped up", Toast.LENGTH_LONG).show();
                        return true; // We 'handled' this event.
                    }

                    @Override
                    public boolean onItemLongPress(int index, PeepshowOverlayItem item) {
                        Toast.makeText(
                                DropInfoMapFragment.this.getActivity(),
                                "Item '" + item.getTitle() + "' (index=" + index
                                        + ") got long pressed", Toast.LENGTH_LONG).show();

                        return false;
                    }

                }, mResourceProxy);
        mMapView.getOverlays().add(this.mMyLocationOverlay);
        mMapView.invalidate();
        setHasOptionsMenu(true);
    }

    /**
     * TODO - Response and GraphUser should not bleed into Fragments. Ensure to refactor out GraphUser and Response from the bus events.
     *
     * @param event
     */
    @Subscribe
    public void onUserLoginCompleteEvent(UserActivitySummaryManager.UserLoginCompleteEvent event) {

        boolean success = event.ismSuccess();
        if (!success) {
            Log.e("MapFragment", "User login has failed");
        }
        GraphUser graphUser = event.getmGraphUser();
        Response response = event.getmFacebookResponse();
        DropInfoDTO dropInfo = event.getmDropInfoDto();

//        mTextView.setText("name: " + graphUser.getName() + " id: " + graphUser.getId());
    }
}
