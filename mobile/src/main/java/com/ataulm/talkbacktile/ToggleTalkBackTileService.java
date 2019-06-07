/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ataulm.talkbacktile;

import android.graphics.drawable.Icon;
import android.os.Handler;
import android.os.Looper;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.DrawableRes;

import com.novoda.accessibility.AccessibilityServices;

public class ToggleTalkBackTileService extends TileService {

    private static final String TAG = ToggleTalkBackTileService.class.getName();
    private static final int UPDATE_DELAY_MILLIS = 1500;

    private AccessibilityServices a11yServices;
    private Toggler toggler;

    @Override
    public void onStartListening() {
        super.onStartListening();
        a11yServices = AccessibilityServices.newInstance(this);
        toggler = new Toggler(getContentResolver(), new Toggler.Callback() {
            @Override
            public void onSecurityExceptionThrown() {
                String message = getString(R.string.permission_required_toast);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                Log.d(TAG, message);
            }
        });

        updateTile(a11yServices.isSpokenFeedbackEnabled());
    }

    @Override
    public void onClick() {
        super.onClick();
        boolean spokenFeedbackEnabled = a11yServices.isSpokenFeedbackEnabled();
        updateTile(spokenFeedbackEnabled);

        if (spokenFeedbackEnabled) {
            toggler.disableTalkBack();
        } else {
            toggler.enableTalkBack();
        }

        updateTileAfterDelay();
    }

    private void updateTileAfterDelay() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean spokenFeedbackEnabled = a11yServices.isSpokenFeedbackEnabled();
                updateTile(spokenFeedbackEnabled);
            }
        }, UPDATE_DELAY_MILLIS);
    }

    private void updateTile(boolean spokenFeedbackEnabled) {
        Tile tile = getQsTile();
        tile.setIcon(Icon.createWithResource(this, getIconRes(spokenFeedbackEnabled)));
        tile.setState(spokenFeedbackEnabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.updateTile();
    }

    @DrawableRes
    private int getIconRes(boolean spokenFeedbackEnabled) {
        return spokenFeedbackEnabled ? R.drawable.talkback_enabled : R.drawable.talkback_disabled;
    }

}
