package com.kenant42.kotlintoggleflashlight

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager

class FlashlightController(private val context: Context) {

    private var isFlashlightOn = false
    private val cameraManager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private val cameraId: String = cameraManager.cameraIdList[0] // Genellikle 0. kamera fenerdir

    fun toggleFlashlight() {
        try {
            if (isFlashlightOn) {
                turnFlashlightOff()
            } else {
                turnFlashlightOn()
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun turnFlashlightOn() {
        try {
            cameraManager.setTorchMode(cameraId, true)
            isFlashlightOn = true
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun turnFlashlightOff() {
        try {
            cameraManager.setTorchMode(cameraId, false)
            isFlashlightOn = false
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}
