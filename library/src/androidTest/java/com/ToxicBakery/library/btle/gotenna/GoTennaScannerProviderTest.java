package com.ToxicBakery.library.btle.gotenna;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.filters.RequiresDevice;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ToxicBakery.android.version.Is;
import com.ToxicBakery.android.version.SdkVersion;
import com.ToxicBakery.library.btle.scanning.IBluetoothAdapter;
import com.ToxicBakery.library.btle.scanning.ILeScanBinder;
import com.ToxicBakery.library.btle.scanning.ILeScanCallback;
import com.ToxicBakery.library.btle.scanning.ILeScanConfiguration;
import com.ToxicBakery.library.btle.scanning.ScanResultCompat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Validate scanner functionality
 */
@RunWith(AndroidJUnit4.class)
@RequiresDevice
@MediumTest
public class GoTennaScannerProviderTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Context context = getContext();
        if (LeUtility.isBluetoothEnabled(context)
                && !LeUtility.isBluetoothEnabled(context)) {

            // Device supports LE but radio is off so tests can not run.
            fail();
        }
    }

    @Test
    public void testStartScan() throws Exception {
        Context context = getContext();
        if (!LeUtility.isLeSupported(context)) {
            return;
        }

        IGoTennaScannerProvider scannerProvider = new GoTennaScannerProvider(context);
        ILeScanBinder leScanBinder = scannerProvider.startScan(new ILeScanCallback() {
            @Override
            public void onDeviceFound(@NonNull ScanResultCompat scanResult) {
            }

            @Override
            public void onDeviceLost(@NonNull ScanResultCompat scanResult) {
            }
        });

        leScanBinder.stop();
    }

    @Test
    public void testCreateScanConfiguration() throws Exception {
        Context context = getContext();
        if (!LeUtility.isLeSupported(context)) {
            return;
        }

        GoTennaScannerProvider scannerProvider = new GoTennaScannerProvider(context);
        ILeScanConfiguration scanConfiguration = scannerProvider.createScanConfiguration();

        IBluetoothAdapter bluetoothAdapter = scanConfiguration.getBluetoothAdapter();
        assertNotNull(bluetoothAdapter);

        ScanSettings scanSettings = scanConfiguration.getScanSettings();
        if (Is.greaterThanOrEqual(SdkVersion.LOLLIPOP)) {
            assertNotNull(scanSettings);
        } else {
            assertNull(scanSettings);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void testCreateFilters() throws Exception {
        GoTennaScannerProvider scannerProvider = new GoTennaScannerProvider(getContext());
        List<ScanFilter> filters = scannerProvider.createFilters();

        if (Is.greaterThanOrEqual(SdkVersion.LOLLIPOP)) {
            assertEquals(1, filters.size());

            ScanFilter scanFilter = filters.get(0);
            assertNotNull(scanFilter);

            ParcelUuid parcelUuid = scanFilter.getServiceUuid();
            assertNotNull(parcelUuid);

            assertEquals(IGoTennaScannerProvider.SERVICE_UUID, parcelUuid.getUuid());
        } else {
            assertEquals(0, filters.size());
        }
    }

    /**
     * Get the target application context provided by the test rule.
     *
     * @return target context
     */
    private static Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

}