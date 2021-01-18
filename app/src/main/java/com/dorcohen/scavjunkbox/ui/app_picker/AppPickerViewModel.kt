package com.dorcohen.scavjunkbox.ui.app_picker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.app_picker.AppPicker
import com.dorcohen.scavjunkbox.app_picker.IAppPicker
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.data.model.SystemMessage
import com.dorcohen.scavjunkbox.data.repository.IRepository
import kotlinx.coroutines.launch

class AppPickerViewModel(private val repository: IRepository) : ViewModel(),IAppPicker by AppPicker {
    fun addApp(appInfo:AppInfo,nav:NavController) = viewModelScope.launch {
        val appList = repository.getAppsBlocking()

        if(appInList(appInfo,appList)){
            with(repository.systemUtil){
                val message = SystemMessage(getString(R.string.error_app_allready_in_list))
                setSystemMessage(message)
            }
        }else {
            repository.addNewApp(appInfo)
            nav.navigateUp()
        }
    }

    private fun appInList(app:AppInfo,list:List<AppInfo>):Boolean{
        list.forEach {
            if(app.packageName == it.packageName){
                return true
            }
        }
        return false
    }
}