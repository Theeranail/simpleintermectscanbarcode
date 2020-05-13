package com.khuntheeranai.intermecbarcodereaderlib

import android.content.Context
import android.widget.Toast
import com.intermec.aidc.*
import com.khuntheeranai.intermecbarcodereaderlib.models.BarcodeModel

class BarcodeScanReader(context: Context) : BarcodeReadListener, AidcManager.IServiceListener {

    private  var wedge: com.intermec.aidc.VirtualWedge? = null;
    private var context: Context;

    var bcr:  com.intermec.aidc.BarcodeReader? = null;
    var barcodeReaderEventListener: BarcodeReaderEventListener? = null

    init {
        this.context = context;
        this.initBarcode()
    }

    override fun barcodeRead(barcodeReadEvent: BarcodeReadEvent?) {
        Toast.makeText(this.context,"barcodeRead",Toast.LENGTH_LONG).show();
        barcodeReadEvent?.let {
            var dataBarcode = BarcodeModel(
                deviceId = it.deviceId,
                barcodeData = it.barcodeData,
                symbolgyId = it.symbolgyId,
                symbologyName = it.symbologyName,
                udsi = it.udsi,
                aim = it.aim,
                codeMark = it.codeMark,
                timestamp = it.timestamp
            );
            Toast.makeText(this.context,"barcodeRead : ${it.barcodeData}",Toast.LENGTH_LONG).show();
            barcodeReaderEventListener?.barcodeReaderSuccess(dataBarcode)
        }
        if (barcodeReadEvent == null) {
            Toast.makeText(this.context,"barcodeRead : barcodeReaderFailed",Toast.LENGTH_LONG).show();
            barcodeReaderEventListener?.barcodeReaderFailed()
        }
    }

    override fun onConnect() {
        Toast.makeText(this.context,"onConnect",Toast.LENGTH_LONG).show();
        try {
            this.bcr = BarcodeReader()
            this.wedge = VirtualWedge()
            this.wedge?.let {
                it.setEnable(false)
            }
        } catch (e: BarcodeReaderException) {
            e.printStackTrace()
            Toast.makeText(this.context,"BarcodeReaderException : ${e.errorMessage}",Toast.LENGTH_LONG).show();
        } catch (e: VirtualWedgeException) {
            e.printStackTrace()
            Toast.makeText(this.context,"VirtualWedgeException : ${e.errorMessage}",Toast.LENGTH_LONG).show();
        }
    }

    override fun onDisconnect() {
        Toast.makeText(this.context,"onDisconnect",Toast.LENGTH_LONG).show();
    }

    fun startScan() {
        this.bcr?.let {
            Toast.makeText(this.context,"  startScan setScannerEnable ",Toast.LENGTH_LONG).show();
            try {
                it.setScannerEnable(true)
                it.addBarcodeReadListener(this)
            } catch (e: BarcodeReaderException) {
                e.printStackTrace()
                Toast.makeText(this.context,"BarcodeReaderException  startScan : ${e.errorMessage}",Toast.LENGTH_LONG).show();
            }
        }
    }

    fun disConnectScanner() {
        try {
            this.wedge?.let {
                it.setEnable(true)
                this.wedge = null
            }
            this.bcr?.let {
                it.close()
                this.bcr = null
            }
        } catch (e: VirtualWedgeException) {
            e.printStackTrace()
        }
        //disconnect from data collection service
        AidcManager.disconnectService()
    }

     fun initBarcode() {
        AidcManager.connectService(this.context, this)
    }
}