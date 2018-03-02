package com.artema;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.digitaltouchsystems.snap.MainActivity;

public class AppRestartPlugin extends CordovaPlugin {
  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("restart")) {
      this.restart(callbackContext);
      return true;
    }
    else if (action.equals("autorestart")) {
      this.restart(callbackContext);
      return true;
    }

    return false;
  }

    public void restart(CallbackContext callbackContext) {
      final Context context = this.cordova.getActivity();

      try {
        Intent mStartActivity = new Intent(context, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, mPendingIntent);
        System.exit(0);

        JSONObject json = new JSONObject();
        callbackContext.success(json);
      }
      catch (Exception e) {
        callbackContext.error("Failed to restart :" + e.getMessage());
      }
    }
}
