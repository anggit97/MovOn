package com.eoa.tech.core.util.ext

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * Created by Anggit Prayogo on 24,July,2020
 * GitHub : https://github.com/anggit97
 */
fun Activity.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun <T> Fragment.viewLifecycle(): ReadWriteProperty<Fragment, T> =
    object : ReadWriteProperty<Fragment, T>, LifecycleObserver {

        // A backing property to hold our value
        private var binding: T? = null

        init {
            // Observe the View Lifecycle of the Fragment
            // * See Gist for full code *
            this@viewLifecycle
                .viewLifecycleOwnerLiveData
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            // Clear out backing property just before onDestroyView
            binding = null
        }

        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
        ): T {
            // Return the backing property if it's set
            return this.binding!!
        }

        override fun setValue(
            thisRef: Fragment,
            property: KProperty<*>,
            value: T
        ) {
            // Set the backing property
            this.binding = value
        }
    }
