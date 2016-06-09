package com.mrstark.gopublic.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import com.mrstark.gopublic.MainActivity
import com.mrstark.gopublic.R

class TakePictureDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog? {
        val builder = AlertDialog.Builder(activity);
        builder.setTitle(R.string.take_picture)
                .setItems(R.array.add_actions) { dialog, which -> selectPicture(which)}
        return builder.create();
    }

    fun selectPicture(i: Int) {
        when(i) {
            0 -> (activity as MainActivity).takeAPhoto()
            1 -> (activity as MainActivity).loadImages()
        }
    }

}
