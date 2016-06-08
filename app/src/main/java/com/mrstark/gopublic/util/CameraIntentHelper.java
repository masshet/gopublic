package com.mrstark.gopublic.util;

import android.app.Activity;
import android.content.Intent;

import com.mrstark.gopublic.activity.GalleryActivity;

import ly.img.android.ui.activities.CameraPreviewIntent;
import ly.img.android.ui.activities.PhotoEditorIntent;

/**
 * Created by mrstark on 6/1/16.
 */
public class CameraIntentHelper {

    public CameraPreviewIntent getCameraIntent(Activity activity) {
        return new CameraPreviewIntent(activity)
                .setExportDir(CameraPreviewIntent.Directory.DCIM, "ImgLyExample")
                .setExportPrefix("example_")
                .setEditorIntent(
                        new PhotoEditorIntent(activity)
                                .setExportDir(PhotoEditorIntent.Directory.DCIM, "ImgLyExample")
                                .setExportPrefix("result_")
                                .destroySourceAfterSave(true)
                );
    }

    public PhotoEditorIntent getPhotoEditorIntent(Activity activity, String path) {
        return new PhotoEditorIntent(activity)
                .setSourceImagePath(path)
                .setExportDir(PhotoEditorIntent.Directory.DCIM, "ImgLyExample")
                .setExportPrefix("result_")
                .destroySourceAfterSave(true);
    }
}
