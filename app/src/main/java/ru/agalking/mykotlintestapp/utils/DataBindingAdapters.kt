package ru.agalking.mykotlintestapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    Picasso.get()
        .load(
            imageUrl.replace(
                "http://",
                "https://"
            )
        ) //                .placeholder(R.drawable.placeholder)
        .into(view)
}

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}
