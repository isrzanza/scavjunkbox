package com.dorcohen.scavjunkbox.ui.junkbox

import androidx.lifecycle.ViewModel
import com.dorcohen.scavjunkbox.data.repository.IRepository

class JunkBoxViewModel(repository: IRepository) : ViewModel() {
    val scavLines = repository.scavLines
}