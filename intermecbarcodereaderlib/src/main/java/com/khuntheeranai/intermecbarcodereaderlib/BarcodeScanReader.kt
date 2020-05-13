package com.khuntheeranai.intermecbarcodereaderlib

import android.content.Context
import com.intermec.aidc.*
import com.khuntheeranai.intermecbarcodereaderlib.models.BarcodeModel

class BarcodeScanReader(context: Context,barcodeReaderEventListener: BarcodeReaderEventListener) : BarcodeReadListener, AidcManager.IServiceListener {

    private var wedge: VirtualWedge? = null;
    private var context: Context;

    var bcr: BarcodeReader? = null;
    var barcodeReaderEventListener: BarcodeReaderEventListener? = null

    init {
        this.context = context;
        this.initBarcode(barcodeReaderEventListener)
    }

    override fun barcodeRead(barcodeReadEvent: BarcodeReadEvent?) {
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
            barcodeReaderEventListener?.barcodeReaderSuccess(dataBarcode)
        }
        if (barcodeReadEvent == null) {
            barcodeReaderEventListener?.barcodeReaderFailed()
        }
    }

    override fun onConnect() {
        try {
            this.bcr = BarcodeReader()
            this.wedge = VirtualWedge()
            this.bcr?.let {
                try {
                    it.setScannerEnable(true)
                    it.addBarcodeReadListener(this)
                } catch (e: BarcodeReaderException) {
                    e.printStackTrace()
                }
            }
        } catch (e: BarcodeReaderException) {
            e.printStackTrace()
        } catch (e: VirtualWedgeException) {
            e.printStackTrace()
        }
    }

    override fun onDisconnect() {

    }

    fun startScan() {
        this.bcr?.let {
            try {
                it.setScannerEnable(true)
                it.addBarcodeReadListener(this)
            } catch (e: BarcodeReaderException) {
                e.printStackTrace()
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

     fun initBarcode(barcodeReaderEventListener:BarcodeReaderEventListener) {
        this.barcodeReaderEventListener = barcodeReaderEventListener
        AidcManager.connectService(this.context, this)
    }
}