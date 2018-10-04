package com.gainwise.permissiontest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    //checks camera permissions and displays toast of status
    fun checkCamera(v: View){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED){
            doToast("Camera not granted")
        }else{
            doToast("Camera granted")
        }
    }

    //checks storage permissions and displays toast of status
    fun checkStorage(v: View){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            doToast("Write Storage not granted")
        }else{
            doToast("Write Storage granted")
        }
    }

    //creates parent directory where we will store our file
    fun createDirectory(v: View){
        val sdCard = Environment.getExternalStorageDirectory()
        val dir = File(sdCard.absolutePath + "/PermissionsTest")
        dir.mkdirs()
        doToast("Successfully created directory...or did it? See the log")
        Log.i("PermissionsVideo", "the parent directory created to store the file " +
                "in is $dir")
    }

    //launches the camera - this requires permission
    fun launchCamera(v: View){
       startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    //simple toast method
    fun doToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    //grabs the textfile that ships with the app and writes it to our new directory we created

    fun placeFileInDirectory(v: View){
        val sdCard = Environment.getExternalStorageDirectory()
        val dir = File(sdCard.absolutePath + "/PermissionsTest")
        val fileName = String.format("PermissionsTest.txt")
        val outFile = File(dir, fileName)

        Log.i("PermissionsVideo", "full path and filename is ${outFile.toString()}")
        try {
            val textFile = assets.open("PermissionsTest.txt")
            val out = FileOutputStream(outFile)
            out.write(textFile.read())
            out.flush()
            out.close()
            textFile.close()

            doToast("Successfully wrote file to storage")

            Log.i("PermissionsVideo", "Successfully wrote file to storage")

        } catch (e: IOException) {
            Log.i("PermissionsVideo", "Exception caught while writing file because of reason: \n\n ${e.message}")
            doToast("Failed to write file to storage - see the log report.")
        }

    }

    fun requestRuntimePermissions(v: View){
        val permissions = arrayOf("android.permission.WRITE_EXTERNAL_STORAGE","android.permission.CAMERA")
        ActivityCompat.requestPermissions(this, permissions, 0)
    }
}
