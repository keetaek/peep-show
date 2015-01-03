package com.kakaw.peepshow.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapView;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

/**
 * Created by keetaekhong on 1/2/15.
 */

public class PeepshowItemizedOverlay<Item extends OverlayItem> extends ItemizedIconOverlay<Item> implements
        ItemizedOverlay.OnFocusChangeListener {

    private boolean mFocusChanged = false;
    private View mPopupView = null;

    public PeepshowItemizedOverlay(List<Item> pList, OnItemGestureListener pItemGestureListener, ResourceProxy pResourceProxy) {
        super(pList, pItemGestureListener, pResourceProxy);
        populate();
        setOnFocusChangeListener(this);
    }


    @Override
    public void onFocusChanged(ItemizedOverlay<?> overlay, OverlayItem newFocus) {
        mFocusChanged = true;
    }

    @Override
    protected boolean onTap(int index) {
        setFocus(getItem(index));
        return true;
    }


    @Override
    protected void draw(Canvas c, MapView mapView, boolean shadow) {
        if (mFocusChanged) {
            mFocusChanged = false;

            // Remove any current focus
            if (mPopupView != null)
                mapView.removeView(mPopupView);

            Item item = this.getFocus();
            if (item != null) {
                mPopupView = getPopupView(mapView.getContext(), item);
                MapView.LayoutParams lp = new MapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, item.getPoint(),
                        MapView.LayoutParams.TOP_CENTER, 0, 0);
                mapView.addView(mPopupView, lp);
            }
        }
        super.draw(c, mapView, shadow);
    }

    protected View getPopupView(Context context, Item item) {
        TextView tv = new TextView(context);
        tv.setText(item.getTitle());
        tv.setBackgroundColor(Color.BLACK);
        return tv;
    }

    @Override
    protected void onDrawItem(Canvas canvas, Item item, Point curScreenCoords,
                              final float aMapOrientation) {
        super.onDrawItem(canvas, item, curScreenCoords, aMapOrientation);
    }

    @Override
    public boolean onSnapToItem(int arg0, int arg1, Point arg2, IMapView arg3) {
        return false;
    }
}
