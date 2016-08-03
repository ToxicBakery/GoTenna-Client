package com.ToxicBakery.library.btle.gotenna.app.scan;

import com.ToxicBakery.library.btle.scanning.ILeScanCallback;

/**
 * Supports register and unregister of scan callbacks and as a scan callback, passes responses to
 * registered callbacks.
 */
public interface IRegistrableLeScanCallback extends ILeScanCallback, IScanCallbackRegistration {
}
