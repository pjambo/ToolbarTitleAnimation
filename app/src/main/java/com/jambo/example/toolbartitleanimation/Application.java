package com.jambo.example.toolbartitleanimation;


import com.jambo.example.toolbartitleanimation.font.FontsOverride;

/**
 * Created by JAMBO on 4/21/15.
 */
public final class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * Apply custom font to entire app
         */
        FontsOverride.setDefaultFont(this, "DEFAULT", "roboto_condensed.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "roboto_condensed.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "roboto_condensed.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "roboto_condensed.ttf");
    }
}
