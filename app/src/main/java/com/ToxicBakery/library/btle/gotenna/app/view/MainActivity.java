package com.ToxicBakery.library.btle.gotenna.app.view;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.ToxicBakery.library.btle.gotenna.app.R;
import com.ToxicBakery.library.btle.gotenna.app.adapter.ScanResultAdapter;
import com.ToxicBakery.library.btle.gotenna.app.presenter.MainActivityPresenter;
import com.ToxicBakery.library.btle.scanning.ScanResultCompat;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;
import timber.log.Timber;

/**
 * Launch activity for the application displaying.
 */
@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends NucleusAppCompatActivity<MainActivityPresenter> {

    private ScanResultAdapter adapter;
    private Button toggleScanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = getPresenter().getAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

        toggleScanButton = (Button) findViewById(R.id.activity_main_toggle_scan);
        toggleScanButton.setOnClickListener(getPresenter().getToggleScanClickListener());
    }

    /**
     * Set scanning enabled.
     *
     * @param flag true to enable
     */
    public void setScanEnabled(boolean flag) {
        toggleScanButton.setEnabled(flag);
    }

    /**
     * Set the scan button text value.
     *
     * @param resId string id to display
     */
    public void setScanButton(@StringRes int resId) {
        toggleScanButton.setText(resId);
    }

    /**
     * Add a result to the adapter.
     *
     * @param scanResultCompat to add
     */
    public void onAddItem(ScanResultCompat scanResultCompat) {
        adapter.addItem(scanResultCompat);
    }

    /**
     * Remove a result from the adapter.
     *
     * @param scanResultCompat to remove
     */
    public void onRemoveItem(ScanResultCompat scanResultCompat) {
        if (!adapter.removeItem(scanResultCompat)) {
            Timber.e("Failed to remove unknown result: %s", scanResultCompat.toString());
        }
    }

}
