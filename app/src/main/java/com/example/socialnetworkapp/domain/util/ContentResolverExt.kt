package com.example.socialnetworkapp.domain.util

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
fun ContentResolver.getFileName(uri: Uri): String {
    val returnCursor = query(uri, null, null, null) ?: return ""
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val fileName = returnCursor.getString(nameIndex)
    returnCursor.close()
    return fileName
}