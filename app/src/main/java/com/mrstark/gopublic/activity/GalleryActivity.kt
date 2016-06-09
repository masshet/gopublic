package com.mrstark.gopublic.activity

import android.os.Bundle
import ly.img.android.ui.activities.CameraPreviewActivity
import ly.img.android.ui.widgets.buttons.GalleryButton

class GalleryActivity : CameraPreviewActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onOpenGallery(GalleryButton(this))
    }
}