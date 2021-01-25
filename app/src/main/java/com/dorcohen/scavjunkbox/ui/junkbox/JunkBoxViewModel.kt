package com.dorcohen.scavjunkbox.ui.junkbox

import androidx.lifecycle.ViewModel
import com.dorcohen.scavjunkbox.data.model.SystemMessage
import com.dorcohen.scavjunkbox.data.model.nameNoInt
import com.dorcohen.scavjunkbox.data.model.nameNumber
import com.dorcohen.scavjunkbox.data.repository.IRepository

class JunkBoxViewModel(private val repository: IRepository) : ViewModel() {

    val scavLines =
        repository
            .scavLines
            .sortedWith(Comparator { a, b ->
                when {
                    a.nameNoInt() > b.nameNoInt() -> 1
                    a.nameNoInt() < b.nameNoInt() -> -1
                    a.nameNoInt() == b.nameNoInt() && a.nameNumber() > b.nameNumber() -> 1
                    a.nameNoInt() == b.nameNoInt() && a.nameNumber() < b.nameNumber() -> -1
                    else -> 0
                }
            })

    fun setStatus(string: String) = repository.systemUtil.setSystemMessage(SystemMessage(string))
}