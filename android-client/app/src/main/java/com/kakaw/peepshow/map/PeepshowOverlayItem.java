package com.kakaw.peepshow.map;

import android.graphics.drawable.Drawable;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public class PeepshowOverlayItem extends OverlayItem {

    public PeepshowOverlayItem(String aUid, String aTitle, String aDescription, GeoPoint aGeoPoint,
                             Drawable aMarker, HotspotPlace aHotspotPlace) {
        super(aUid, aTitle, aDescription, aGeoPoint);
        this.setMarker(aMarker);
        this.setMarkerHotspot(aHotspotPlace);
    }
}
