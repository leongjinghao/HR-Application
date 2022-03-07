package com.example.frontend

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

class ImageSaver(private var context: Context) {
    private var directoryName : String = "images"
    private var fileName : String = "image.png"

    fun setFileName (fileName : String) : ImageSaver {
        this.fileName = fileName
        return this
    }

    fun setDirectoryName (directoryName : String) : ImageSaver {
        this.directoryName = directoryName
        return this
    }

    fun save(bitmapImage : Bitmap) {
        var fileOutputStream : FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(createFile())
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        } catch (e : Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e : IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun createFile() : File {
        val directory : File = context.getDir(directoryName, Context.MODE_PRIVATE)
        if (!directory.exists() && !directory.mkdirs()) {
            Log.e("ImageSave", "Error creating directory $directory")
        }
        return File(directory, fileName)
    }

    fun load() : Bitmap? {
        var fileInputStream : FileInputStream? = null
        try {
            fileInputStream = FileInputStream(createFile())
            return BitmapFactory.decodeStream(fileInputStream)
        } catch (e : Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileInputStream?.close()
            } catch (e : IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

}