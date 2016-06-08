package com.mrstark.gopublic.activity

import android.os.Bundle
import ly.img.android.sdk.configuration.PhotoEditorSdkConfig
import ly.img.android.ui.activities.CameraPreviewActivity
import ly.img.android.ui.activities.PhotoEditorActivity
import ly.img.android.ui.widgets.buttons.GalleryButton

/**
 * Created by mrstark on 6/2/16.
 */
class GalleryActivity : CameraPreviewActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onOpenGallery(GalleryButton(this))
    }
}