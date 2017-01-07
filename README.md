# talkback-toggle-tile

A quick setting tile (Android N+) to toggle the state of TalkBack.

<img src="web_hi_res_512.png" align="middle">

The app needs the Write Secure Settings permission to change the state of accessibility services. After installing the APK, run this command on your PC to grant the permission:

```bash
$ adb shell pm grant com.ataulm.talkbacktile android.permission.WRITE_SECURE_SETTINGS
```

This is different from the "pause"/"suspend" setting built into TalkBack because this one actually turns the service off, meaning when you [query for the TalkBack status in an app](https://www.novoda.com/blog/accessibilitools/), it's legitimately turned off.

---

Thanks Nick Butcher for [the reference project](https://github.com/nickbutcher/AnimatorDurationTile), Ian Lake for [the article](https://medium.com/google-developers/quick-settings-tiles-e3c22daf93a8), and Alan Viverette for the hint to look at [ServiceControlUtils](https://android.googlesource.com/platform/cts/+/master/tests/accessibility/src/android/view/accessibility/cts/ServiceControlUtils.java).

