package com.khuntheeranai.intermecbarcodereaderlib

import com.khuntheeranai.intermecbarcodereaderlib.models.BarcodeModel


interface BarcodeReaderEventListener {
    fun barcodeReaderSuccess(barcodeModel: BarcodeModel)
    fun barcodeReaderFailed(){

    }
}