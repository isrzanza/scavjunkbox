package com.dorcohen.scavjunkbox.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.data.model.NavigationDestination
import com.dorcohen.scavjunkbox.data.model.navigatedTo
import com.dorcohen.scavjunkbox.data.repository.IRepository

class MainViewModel(repository: IRepository) : ViewModel() {
    val systemMessage = repository.systemUtil.getSystemMessage()

    private var destination = NavigationDestination(R.id.junkBoxFragment, R.id.junkBoxFragment)

    fun navigateTo(navController: NavController, destinationId: Int): Boolean {
        if (destinationId != destination.current) {
            destination = destination.navigatedTo(destinationId)
            getBottomNavDestination()?.apply {
                navController.navigate(this)
            }
            return true
        }
        return false
    }

    private fun getBottomNavDestination(): Int? =
        when (destination.previous) {
            R.id.junkBoxFragment -> {
                when (destination.current) {
                    R.id.notificationsFragment -> R.id.action_global_notificationsFragment
                    R.id.aboutFragment -> R.id.action_global_aboutFragment
                    else -> null
                }
            }
            R.id.notificationsFragment -> {
                when (destination.current) {
                    R.id.junkBoxFragment -> R.id.action_notificationsFragment_to_junkBoxFragment
                    R.id.aboutFragment -> R.id.action_global_aboutFragment
                    else -> null
                }
            }
            R.id.aboutFragment -> {
                when (destination.current) {
                    R.id.notificationsFragment -> R.id.action_global_notificationsFragment
                    R.id.junkBoxFragment -> R.id.action_aboutFragment_to_junkBoxFragment
                    else -> null
                }
            }
            else -> null
        }
}