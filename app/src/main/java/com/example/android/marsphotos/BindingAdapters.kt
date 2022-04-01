package com.example.android.marsphotos

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsphotos.network.MarsPhoto
import com.example.android.marsphotos.overview.MarsApiStatus
import com.example.android.marsphotos.overview.PhotoGridAdapter

@BindingAdapter("listData")
fun RecyclerView.loadList(data: List<MarsPhoto>?) {
    data?.let {
        val adapter = adapter as PhotoGridAdapter
        adapter.submitList(data)
    }
}

@BindingAdapter("imageUrl")
fun ImageView.loadImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        this.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("marsApiStatus")
fun ImageView.loadStatus(status: MarsApiStatus?) {
    status?.let {
        when (it) {
            MarsApiStatus.ERROR -> {
                visibility = View.VISIBLE
                setImageResource(R.drawable.ic_connection_error)
            }
            else -> visibility = View.GONE
        }
    }
}

@BindingAdapter("marsApiStatus")
fun ProgressBar.loadStatus(status: MarsApiStatus?) {
    status?.let {
        visibility = when (it) {
            MarsApiStatus.LOADING -> {
                View.VISIBLE
            }
            else -> View.GONE
        }
    }
}

@BindingAdapter("errorMsg", "marsApiStatus")
fun TextView.bindError(errorMsg: String?, status: MarsApiStatus?) {
    status?.let { it ->
        when (it) {
            MarsApiStatus.ERROR -> {
                visibility = View.VISIBLE
                text = errorMsg
            }
            else -> visibility = View.GONE
        }
    }
}