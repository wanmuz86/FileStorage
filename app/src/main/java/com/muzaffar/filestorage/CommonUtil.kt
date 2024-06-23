package com.muzaffar.filestorage

import android.os.Environment

object CommonUtil {

    fun isReadable():Boolean {
        var mExternalStorangeAvailable = false
        try {
            val state = Environment.getExternalStorageState()
            // Jikalau phone saya ada externalStorage yang boleh read and write
            if (Environment.MEDIA_MOUNTED.equals(state)){
                mExternalStorangeAvailable = true
            }
            // Jikalau phone saya ada externalStorage yang boleh read sahaja
            else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
                mExternalStorangeAvailable = true
            }
            else {
                // Phone tidak ada externalStorage
                mExternalStorangeAvailable = false
            }

        }
        catch (e:Exception){
            print(e.message)
        }
        return mExternalStorangeAvailable
    }
}