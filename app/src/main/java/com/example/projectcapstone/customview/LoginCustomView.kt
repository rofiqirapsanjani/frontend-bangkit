package com.example.projectcapstone.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class LoginCustomView : TextInputEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }


    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (c.toString().isEmpty()) {
                    error = "Wajib Diisi"
                } else {
                    error = null
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                error = null
            }

            override fun afterTextChanged(e: Editable?) {
                if (e.toString().length < 5) {
                    error = "Password invalid"
                } else {
                    error = null
                }
            }

        })
    }
}
