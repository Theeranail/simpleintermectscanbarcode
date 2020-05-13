package com.khuntheeranai.intermecbarcodereaderlib.models

data class BarcodeModel(
        var deviceId: String,
        var barcodeData: String,
        var symbolgyId: String,
        var symbologyName: String,
        var udsi: String,
        var aim: String,
        var codeMark: String,
        var timestamp: String
) {
}