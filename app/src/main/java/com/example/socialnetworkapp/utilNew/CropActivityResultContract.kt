package com.example.socialnetworkapp.utilNew

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import com.example.socialnetworkapp.domain.util.getFileName
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCrop.RESULT_ERROR
import java.io.File

class CropActivityResultContract(
    private val aspectRatioX: Float,
    private val aspectRatioY: Float,
) : ActivityResultContract<Uri, Uri?>() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun createIntent(
        context: Context,
        input: Uri
    ): Intent {
        return UCrop.of(
             input,
            Uri.fromFile(
                File(
                    context.cacheDir,
                    context.contentResolver.getFileName(input)
                )
            )
        )
            .withAspectRatio(aspectRatioX, aspectRatioY)
            .getIntent(context)
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): Uri? {
        if(intent == null || intent.hasExtra("error")) {
            return null
        }
        if(resultCode == RESULT_ERROR) {
            val error = UCrop.getError(intent)
            error?.printStackTrace()
        }
        return UCrop.getOutput(intent)
    }
}