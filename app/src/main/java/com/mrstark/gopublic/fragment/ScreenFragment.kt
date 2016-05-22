package com.mrstark.gopublic.fragment

import android.app.Fragment
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.mrstark.gopublic.MainActivity
import com.mrstark.gopublic.R
import com.mrstark.gopublic.entity.Screen
import com.squareup.picasso.Picasso

class ScreenFragment(): Fragment() {
    private var dateAndTime: LinearLayout? = null
    private var toolbar: CollapsingToolbarLayout? = null
    private var description: TextView? = null
    private var cost: TextView? = null
    private var workTime: TextView? = null
    private var price: TextView? = null
    private var image: ImageView? = null
    private var photo: ImageView? = null
    private var addPhotoButton: Button? = null
    private var timePicker: TimePicker? = null
    private var datePicker: DatePicker? = null
    private var dateCheckBox: CheckBox? = null
    private var spinner: Spinner? = null
    private var deleteIcon: ImageView? = null
    var screen: Screen? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(R.layout.fragment_screen, container, false)
        init(root)
        initToolbar(root)
        loadData()
        return root
    }

    private fun initToolbar(root: View?) {
        toolbar = root?.findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        toolbar?.title = screen?.address
        toolbar?.setExpandedTitleColor(android.R.color.transparent)
    }

    fun loadPhoto(bitmap: Bitmap) {
        photo?.visibility = View.VISIBLE
        photo?.setImageBitmap(bitmap)
        addPhotoButton?.visibility = View.GONE
        deleteIcon?.visibility = View.VISIBLE
    }

    private fun loadData() {
        Picasso.with(activity).load(screen?.image).into(image)
        description?.text = screen?.description
        cost?.text = screen?.cost.toString()
        workTime?.text = screen?.workTime
        price?.text = screen?.cost.toString()
    }

    private fun init(root: View?) {
        screen = arguments.getParcelable((activity as MainActivity).KEY_SCREEN)

        image = root?.findViewById(R.id.image) as ImageView
        description = root?.findViewById(R.id.description) as TextView
        cost = root?.findViewById(R.id.cost) as TextView
        workTime = root?.findViewById(R.id.work_time) as TextView
        price = root?.findViewById(R.id.full_price) as TextView
        photo = root?.findViewById(R.id.photo) as ImageView

        timePicker = root?.findViewById(R.id.time_picker) as TimePicker
        timePicker?.setIs24HourView(true)

        datePicker = root?.findViewById(R.id.date_picker) as DatePicker

        dateCheckBox = root?.findViewById(R.id.date_checkbox) as CheckBox
        dateCheckBox?.setOnCheckedChangeListener { buttonView, isChecked -> onCheckedChanged(isChecked) }

        addPhotoButton = root?.findViewById(R.id.add_photo) as Button
        addPhotoButton?.setOnClickListener { showDialog()  }

        spinner = root?.findViewById(R.id.seconds_spinner) as Spinner
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                price?.text = ((position + 1) * (screen?.cost!!)).toString()
            }

        }

        deleteIcon = root?.findViewById(R.id.delete_icon) as ImageView
        deleteIcon?.setOnClickListener { deletePicture() }

        dateAndTime = root?.findViewById(R.id.date_and_time_layout) as LinearLayout
    }

    private fun deletePicture() {
        photo?.visibility = View.GONE
        deleteIcon?.visibility = View.GONE
        addPhotoButton?.visibility = View.VISIBLE
    }

    private fun showDialog() {
        var fragment = TakePictureDialogFragment()
        fragment.show(childFragmentManager, "Pictures")
    }

    fun onCheckedChanged(isChecked: Boolean) {
        when(isChecked) {
            true -> dateAndTime?.visibility = View.GONE
            false -> dateAndTime?.visibility = View.VISIBLE
        }
    }
}
