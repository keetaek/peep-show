package com.kakaw.peepshow.helper;

/**
 * Created by keetaekhong on 12/20/14.
 */
public class Constants {
    public interface Login {
        public static String[] FB_READ_PERMISSIONS = {"public_profile", "email", "user_friends"};
        public static String[] FB_PUBLISH_PERMISSIONS = {"publish_actions"};
    }

    public interface Map {
        public static final String PREFS_NAME = "com.kakaw.peepshow.prefs";
        public static final String PREFS_TILE_SOURCE = "tilesource";
        public static final String PREFS_SCROLL_X = "scrollX";
        public static final String PREFS_SCROLL_Y = "scrollY";
        public static final String PREFS_ZOOM_LEVEL = "zoomLevel";
        public static final String PREFS_SHOW_LOCATION = "showLocation";
        public static final String PREFS_SHOW_COMPASS = "showCompass";
    }
}
