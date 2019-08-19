package com.example.finalproject.ethereum;

import android.app.Activity;

import com.google.zxing.integration.android.IntentIntegrator;

public class ScanIntergrator {
    public static IntentIntegrator sIntentIntergrator;

    public ScanIntergrator(Activity activity){
        sIntentIntergrator = new IntentIntegrator(activity);
    }

    public void startScan(){
        sIntentIntergrator.setOrientationLocked(false);
        sIntentIntergrator.setBarcodeImageEnabled(true);
        sIntentIntergrator.initiateScan();
    }
}
