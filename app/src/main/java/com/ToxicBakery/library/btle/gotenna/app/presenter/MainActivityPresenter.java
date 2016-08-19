package com.ToxicBakery.library.btle.gotenna.app.presenter;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.View;

import com.ToxicBakery.android.version.Is;
import com.ToxicBakery.android.version.SdkVersion;
import com.ToxicBakery.library.btle.gotenna.app.ClientApplication;
import com.ToxicBakery.library.btle.gotenna.app.R;
import com.ToxicBakery.library.btle.gotenna.app.adapter.ScanResultAdapter;
import com.ToxicBakery.library.btle.gotenna.app.scan.ScanManager;
import com.ToxicBakery.library.btle.gotenna.app.view.MainActivity;
import com.ToxicBakery.library.btle.scanning.ILeScanCallback;
import com.ToxicBakery.library.btle.scanning.ScanResultCompat;

import javax.inject.Inject;

import nucleus.presenter.Presenter;

/**
 * Presenter for the main activity.
 */
public class MainActivityPresenter extends Presenter<MainActivity> {

    private static final int REQUEST_PERMISSION = 1000;

    @Inject
    Context context;

    @Inject
    ScanManager scanManager;

    private ILeScanCallback leScanCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        ClientApplication.getAppComponent().inject(this);
        leScanCallback = new LeScanCallback();
        scanManager.registerCallback(leScanCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanManager.unregisterCallback(leScanCallback);
    }

    @Override
    protected void onTakeView(MainActivity mainActivity) {
        super.onTakeView(mainActivity);

        present();

        // Request permission
        if (!hasCourseLocationPermission()) {
            ActivityCompat.requestPermissions(
                    mainActivity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSION);
        }
    }

    /**
     * Create and adapter for displaying scan results.
     *
     * @return scan result adapter
     */
    public ScanResultAdapter getAdapter() {
        return new ScanResultAdapter();
    }

    /**
     * Update the UI
     */
    void present() {
        MainActivity mainActivity = getView();
        if (mainActivity == null) {
            return;
        }

        boolean hasPermission = hasCourseLocationPermission();
        mainActivity.setScanEnabled(hasPermission);

        boolean isScanning = scanManager.isScanning();
        mainActivity.setScanButton(isScanning ? R.string.activity_main_scan_stop : R.string.activity_main_scan_start);
    }

    /**
     * Check if the application has permission for course location.
     *
     * @return true if granted
     */
    boolean hasCourseLocationPermission() {
        return hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    /**
     * Check if permission has been granted.
     *
     * @param permission to be checked
     * @return true if granted
     */
    boolean hasPermission(String permission) {
        // Check marshmallow or greater
        if (Is.greaterThanOrEqual(SdkVersion.MARSHMALLOW)) {
            int result = PermissionChecker.checkSelfPermission(context, permission);
            return result == PermissionChecker.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    /**
     * Handle scan click toggling.
     *
     * @return toggle click listener
     */
    public View.OnClickListener getToggleScanClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanManager.toggle();
                present();
            }
        };
    }

    /**
     * Scan callback for passing received scan results to the view
     */
    private class LeScanCallback implements ILeScanCallback {

        @Override
        public void onDeviceFound(@NonNull ScanResultCompat scanResult) {
            MainActivity view = getView();
            if (view != null) {
                view.onAddItem(scanResult);
            }
        }

        @Override
        public void onDeviceLost(@NonNull ScanResultCompat scanResult) {
            MainActivity view = getView();
            if (view != null) {
                view.onRemoveItem(scanResult);
            }
        }

    }

}
