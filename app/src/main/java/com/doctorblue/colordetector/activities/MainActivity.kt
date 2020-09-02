package com.doctorblue.colordetector.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.doctorblue.colordetector.R
import com.doctorblue.colordetector.base.BaseActivity
import com.doctorblue.colordetector.model.Color
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 26
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private lateinit var cameraExecutor: ExecutorService

    private val cameraProviderFuture by lazy {
        ProcessCameraProvider.getInstance(this)
    }

    // Used to bind the lifecycle of cameras to the lifecycle owner
    private val cameraProvider by lazy {
        cameraProviderFuture.get()
    }
    private var isBackCamera = true

    // Select back camera as a default
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA


    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initControls(savedInstanceState: Bundle?) {
        if (allPermissionsGranted()) {

            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun initEvents() {

        btn_pick_color.setOnClickListener {

        }
        btn_add_list_color.setOnClickListener {

        }

        btn_change_camera.setOnClickListener {

            if (isBackCamera) {
                cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                isBackCamera = false
            } else {
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                isBackCamera = true
            }
            startCamera()
        }


    }




    private fun startCamera() {

        cameraProviderFuture.addListener({
            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(camera_preview.createSurfaceProvider())
                }

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, ColorAnalyzer { color ->
                        Log.d(TAG, "Average luminosity: $color")
                    })
                }
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private class ColorAnalyzer(private val listener: (Color) -> Unit) : ImageAnalysis.Analyzer {

        override fun analyze(image: ImageProxy) {

            //for test
            val color = Color()
            listener(color)

            image.close()
        }
    }
}