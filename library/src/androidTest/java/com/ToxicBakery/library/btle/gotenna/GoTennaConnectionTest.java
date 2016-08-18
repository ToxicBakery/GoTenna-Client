package com.ToxicBakery.library.btle.gotenna;

import android.bluetooth.BluetoothGatt;
import android.support.test.filters.RequiresDevice;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

/**
 * Ensure connections report correct state and values
 */
@RunWith(AndroidJUnit4.class)
@RequiresDevice
@SmallTest
public class GoTennaConnectionTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testConnect() throws Exception {
        // FIXME Use a bluetooth device wrapper so it can be mocked
    }

    @Test
    public void testDisconnect() throws Exception {
        // FIXME Use a bluetooth device wrapper so it can be mocked
    }

    @Test
    public void testGetRssi() throws Exception {
        GoTennaGattCallback goTennaGattCallback = mock(GoTennaGattCallback.class);
        Mockito.when(goTennaGattCallback.getLastRssi())
                .thenReturn(42);

        @SuppressWarnings("ConstantConditions")
        GoTennaConnection goTennaConnection = new GoTennaConnection(null, goTennaGattCallback);
        Assert.assertEquals(42, goTennaConnection.getRssi());
    }

    @Test
    public void testGetState() throws Exception {
        GoTennaGattCallback goTennaGattCallback = mock(GoTennaGattCallback.class);
        Mockito.when(goTennaGattCallback.getLastState())
                .thenReturn(BluetoothGatt.STATE_CONNECTING);

        @SuppressWarnings("ConstantConditions")
        GoTennaConnection goTennaConnection = new GoTennaConnection(null, goTennaGattCallback);
        Assert.assertEquals(BluetoothGatt.STATE_CONNECTING, goTennaConnection.getState());
    }

    @Test
    public void testGetStatus() throws Exception {
        GoTennaGattCallback goTennaGattCallback = mock(GoTennaGattCallback.class);
        Mockito.when(goTennaGattCallback.getLastStatus())
                .thenReturn(BluetoothGatt.GATT_FAILURE);

        @SuppressWarnings("ConstantConditions")
        GoTennaConnection goTennaConnection = new GoTennaConnection(null, goTennaGattCallback);
        Assert.assertEquals(BluetoothGatt.GATT_FAILURE, goTennaConnection.getStatus());
    }

}