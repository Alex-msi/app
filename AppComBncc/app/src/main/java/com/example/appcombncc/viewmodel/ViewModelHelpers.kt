package com.example.appcombncc.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel

inline fun <reified VM : ViewModel> Fragment.appViewModel(
    crossinline provider: () -> VM
): Lazy<VM> = viewModels {
    AppViewModelFactory { provider() }
}
