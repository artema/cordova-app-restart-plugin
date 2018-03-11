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
  private static boolean _autorestart;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("restart")) {
      this.restart(callbackContext);
      return true;
    }
    else if (action.equals("autorestart")) {
      this.autorestart(callbackContext);
      return true;
    }

    return false;
  }

    public void restart(CallbackContext callbackContext) {
      try {
        this.doRestart(0);

        JSONObject json = new JSONObject();
        callbackContext.success(json);
      }
      catch (Exception e) {
        callbackContext.error("Failed to restart :" + e.getMessage());
      }
    }

    public void autorestart(CallbackContext callbackContext) {
      try {
        if (!AppRestartPlugin._autorestart) {
          Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
          AppRestartPlugin._autorestart = true;
        }

        JSONObject json = new JSONObject();
        callbackContext.success(json);
      }
      catch (Exception e) {
        callbackContext.error("Failed to autorestart :" + e.getMessage());
      }
    }

    public void doRestart(int delay) {
      final Context context = this.cordova.getActivity().getApplicationContext();

      Intent mStartActivity = new Intent(context, MainActivity.class);

      if (delay == 0) {
        context.startActivity(mStartActivity);
      }
      else {
        // int mPendingIntentId = 123456;
        // PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        // AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        // mgr.set(AlarmManager.RTC, System.currentTimeMillis() + delay, mPendingIntent);
        //System.exit(0);

        Intent intent = new Intent();
        intent.setAction("php.intent.action.REBOOT");
        context.sendBroadcast(intent);
      }
    }
}

class ExceptionHandler implements Thread.UncaughtExceptionHandler {
  private AppRestartPlugin _plugin;

  public ExceptionHandler(AppRestartPlugin plugin) {
    this._plugin = plugin;
  }

  @Override
  public void uncaughtException(Thread thread, Throwable ex) {
    this._plugin.doRestart(10000);
  }
}
