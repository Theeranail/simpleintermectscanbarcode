package com.khuntheeranai.simpleintermectscanbarcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khuntheeranai.intermecbarcodereaderlib.BarcodeReaderEventListener
import com.khuntheeranai.intermecbarcodereaderlib.BarcodeScanReader
import com.khuntheeranai.intermecbarcodereaderlib.models.BarcodeModel

class MainActivity : AppCompatActivity(), BarcodeReaderEventListener {
    private lateinit var barcodeScanReader: BarcodeScanReader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        barcodeScanReader = BarcodeScanReader(this@MainActivity,this);

    }

    override fun barcodeReaderSuccess(barcodeModel: BarcodeModel) {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        super.onStop()
        barcodeScanReader.disConnectScanner();
    }
}
