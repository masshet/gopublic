package com.mrstark.gopublic.fragment

import android.app.Fragment
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.mrstark.gopublic.MainActivity
import com.mrstark.gopublic.R
import com.mrstark.gopublic.entity.Screen
import com.squareup.picasso.Picasso
import org.json.JSONObject
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import retrofit.mime.TypedFile
import retrofit.mime.TypedString
import java.io.File
import java.util.*

class ScreenFragment(): Fragment() {
    private var dateAndTime: LinearLayout? = null
    private var collapsingToolbar: CollapsingToolbarLayout? = null
    private var toolbar: android.support.v7.widget.Toolbar? = null
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
    private var send: Button? = null
    private var agreement: CheckBox? = null
    private var path: String? = null
    private var showTime: Int = 1
    var screen: Screen? = null
    var root: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater?.inflate(R.layout.fragment_screen, container, false)
        init()
        initToolbar()
        loadData()
        return root
    }

    private fun initToolbar() {
        collapsingToolbar = root?.findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbar?.title = screen?.address
        collapsingToolbar?.setExpandedTitleColor(android.R.color.transparent)
        toolbar = root?.findViewById(R.id.toolbar) as Toolbar
        toolbar?.setNavigationOnClickListener { (activity as MainActivity).onBackPressed() }
    }

    fun loadPhoto(path: String) {
        this.path = path
        val file = File(path)
        val bitmap = BitmapFactory.decodeFile(file.toString())
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

    private fun init() {
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
        datePicker?.minDate = Calendar.getInstance().timeInMillis

        dateCheckBox = root?.findViewById(R.id.date_checkbox) as CheckBox
        dateCheckBox?.setOnCheckedChangeListener { buttonView, isChecked -> onCheckedChanged(isChecked) }

        addPhotoButton = root?.findViewById(R.id.add_photo) as Button
        addPhotoButton?.setOnClickListener { showDialog()  }

        spinner = root?.findViewById(R.id.seconds_spinner) as Spinner
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                price?.text = ((position + 1) * (screen?.cost!!)).toString()
                showTime = position + 1
            }

        }

        deleteIcon = root?.findViewById(R.id.delete_icon) as ImageView
        deleteIcon?.setOnClickListener { deletePicture() }

        dateAndTime = root?.findViewById(R.id.date_and_time_layout) as LinearLayout

        agreement = root?.findViewById(R.id.agreement_checkbox) as CheckBox

        send = root?.findViewById(R.id.send) as Button
        send?.setOnClickListener { sendOrder() }
    }

    private fun sendOrder() {
        if (agreement?.isChecked!! == false) {
            Snackbar.make(root?.findViewById(R.id.view_screens)!!, R.string.agreement_false, Snackbar.LENGTH_SHORT).show()
        } else if (path == null) {
            Snackbar.make(root?.findViewById(R.id.view_screens)!!, R.string.choose_image, Snackbar.LENGTH_SHORT).show()
        } else {
            val credentials = activity.getPreferences(AppCompatActivity.MODE_PRIVATE).getString((activity as MainActivity).KEY_CREDENTIAL, "")
            (activity as MainActivity).client.upload(
                    credentials,
                    TypedFile("image/*", File(path)),
                    TypedString(spinner?.firstVisiblePosition.toString()),
                    TypedString(screen?.id.toString()),
                    TypedString(dateCheckBox?.isChecked.toString()),
                    TypedString(getDate()),
                    TypedString(getTime()),
                    TypedString(agreement?.isChecked.toString()),
                    object : Callback<JSONObject> {
                        override fun success(t: JSONObject?, response: Response?) {
                            (activity as MainActivity).loadCabinet()
                        }

                        override fun failure(error: RetrofitError?) {
                            Log.d("MyTag", error?.message)
                        }
                    })
        }
    }

    private fun deletePicture() {
        photo?.visibility = View.GONE
        deleteIcon?.visibility = View.GONE
        addPhotoButton?.visibility = View.VISIBLE
    }

    private fun showDialog() {
        val fragment = TakePictureDialogFragment()
        fragment.show(childFragmentManager, "Pictures")
    }

    fun onCheckedChanged(isChecked: Boolean) {
        when(isChecked) {
            true -> dateAndTime?.visibility = View.GONE
            false -> dateAndTime?.visibility = View.VISIBLE
        }
    }

    fun getDate(): String = datePicker?.dayOfMonth.toString() + "/" + datePicker?.month.toString() + "/" + datePicker?.year.toString()
    fun getTime(): String = timePicker?.hour.toString() + ":" + timePicker?.minute.toString()
}
