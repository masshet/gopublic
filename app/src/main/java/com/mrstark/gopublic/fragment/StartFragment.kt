package com.mrstark.gopublic.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.digits.sdk.android.DigitsAuthButton
import com.mrstark.gopublic.MainActivity
import com.mrstark.gopublic.R

class StartFragment : Fragment() {
    var button: DigitsAuthButton? = null

    var textview: TextView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_start, container, false)
        button = root.findViewById(R.id.authorization_button) as DigitsAuthButton
        textview = root.findViewById(R.id.without_registration) as TextView
        textview?.setOnClickListener { (activity as MainActivity).loadCityScreens() }
        setUpButton()
        return root
    }

    private fun setUpButton() {
        button?.setAuthTheme(android.R.style.ThemeOverlay_Material)
        button?.setCallback((activity as MainActivity).callback)
        button?.setBackgroundColor(resources.getColor(R.color.colorAccent, null))
        button?.setText(R.string.authorization)
    }
}
