package com.ataulm.talkbacktile;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

class Toggler {

    private static final String TALKBACK_SERVICE_NAME = "com.google.android.marvin.talkback/.TalkBackService";
    private static final String VALUE_DISABLED = "0";
    private static final String VALUE_ENABLED = "1";

    private final ContentResolver contentResolver;
    private final Callback callback;

    Toggler(ContentResolver contentResolver, Callback callback) {
        this.contentResolver = contentResolver;
        this.callback = callback;
    }

    void enableTalkBack() {
        // TODO: be cool - don't disable the other a11y services
        try {
            Settings.Secure.putString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, TALKBACK_SERVICE_NAME);
            Settings.Secure.putString(contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED, VALUE_ENABLED);
        } catch (SecurityException se) {
            callback.onSecurityExceptionThrown();
        }
    }

    void disableTalkBack() {
        // TODO: be cool - don't disable all a11y services
        Settings.Secure.putString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, "");
        Settings.Secure.putString(contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED, VALUE_DISABLED);
    }

    interface Callback {

        void onSecurityExceptionThrown();

    }

}
