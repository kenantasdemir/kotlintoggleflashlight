package com.kenant42.kotlintoggleflashlight

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.LauncherActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import com.kenant42.kotlintoggleflashlight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var flashlightController: FlashlightController
    private lateinit var bluetoothAdapter:BluetoothAdapter
    private lateinit var bluetoothManager:BluetoothManager
    private lateinit var activityResultLauncher:ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       bluetoothManager = application.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
 bluetoothAdapter = bluetoothManager.adapter

        flashlightController = FlashlightController(this)

        binding.toggleFlashLight.setOnClickListener {
        flashlightController.toggleFlashlight()
        }

        binding.toggleAirplaneMode.setOnClickListener {
            val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
            startActivity(intent)
        }

        binding.toggleBluetooth.setOnClickListener{
            //turnOnBluetooth()

        }



        binding.turnOffBluetooth.setOnClickListener {
            //turnOffBluetooth()
        }

        binding.btnShow.setOnClickListener {
            //pairedDevices()
            /*
            val intent = Intent(Intent.ACTION_DIAL) // Arama ekranını açmak için
            intent.data = Uri.parse("tel:$12345667678") // Telefon numarasını ayarla
            startActivity(intent)

             */

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = ContactsContract.Contacts.CONTENT_URI
            startActivity(intent)
        }







    }


    @SuppressLint("MissingPermission")
    fun pairedDevices(){
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
            binding.nameTF.setText(deviceName.toString())
            binding.addressTF.setText(deviceHardwareAddress.toString())
        }
    }

    fun turnOnBluetooth() {
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            activityResultLauncher.launch(enableBtIntent)
        } else {
            Toast.makeText(this, "Bluetooth zaten açık.", Toast.LENGTH_SHORT).show()
        }
    }
    fun turnOffBluetooth(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
       bluetoothAdapter.disable()
    }



    companion object {
        private const val REQUEST_ENABLE_BT = 1
    }



}
