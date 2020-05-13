package com.khuntheeranai.simpleintermectscanbarcode

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.khuntheeranai.intermecbarcodereaderlib.BarcodeReaderEventListener
import com.khuntheeranai.intermecbarcodereaderlib.BarcodeScanReader
import com.khuntheeranai.intermecbarcodereaderlib.models.BarcodeModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BarcodeReaderEventListener {
    private lateinit var barcodeScanReader: BarcodeScanReader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //set lock the orientation
        //otherwise, the onDestory will trigger
        //when orientation changes
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        barcodeScanReader = BarcodeScanReader(this);
        barcodeScanReader.barcodeReaderEventListener = this;

        barcodeScanReader.startScan()

    }

    override fun barcodeReaderSuccess(barcodeModel: BarcodeModel) {
        runOnUiThread {
            textViewLog.text = "barcode : => ${barcodeModel.barcodeData} \n"
        }
    }

    override fun barcodeReaderFailed() {
        runOnUiThread {
            textViewError.text = "\n barcodeReaderFailed \n"
        }

    }

    override fun onStop() {
        super.onStop()
        barcodeScanReader.disConnectScanner();
    }
}
