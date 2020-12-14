package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

fun Activity.isKeyboardOpen(): Boolean {
    val r = Rect()
    val rootView = window.decorView
    rootView.getWindowVisibleDisplayFrame(r)
    val screenHeight = rootView.height
    var heightDiff = screenHeight - (r.bottom - r.top)
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        heightDiff -= resources.getDimensionPixelSize(resourceId)
    }
    return false
}

fun Activity.isKeyboardClosed() = !isKeyboardOpen()

fun Activity.hideKeyboard() {

    if (isKeyboardClosed()) {
        val view = this.currentFocus
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
    // else {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    // }
}
