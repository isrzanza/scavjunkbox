package com.dorcohen.scavjunkbox.ui.app_picker

import android.app.Application
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.app_picker.AppPicker
import com.dorcohen.scavjunkbox.app_picker.IAppPicker
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.data.model.SystemMessage
import com.dorcohen.scavjunkbox.data.repository.IRepository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AppPickerViewModel(private val repository: IRepository) : ViewModel(),IAppPicker by AppPicker {
    val searchBoxText:MutableLiveData<String> = MutableLiveData("")
    private val _appList:MutableLiveData<List<AppInfo>> = MutableLiveData(null)
    private val filteredAppList:MediatorLiveData<List<AppInfo>> = MediatorLiveData()

    val appList:LiveData<List<AppInfo>> = filteredAppList

    val progressBarVisible:LiveData<Boolean> = Transformations.map(_appList){
        val res = it == null
        res
    }

    init {
        setupAppListMediator()
    }

    fun refreshAppList(application:Application) = viewModelScope.launch {
        val list = getInstalledAppList(application)
        _appList.postValue(list)
    }

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

    private fun setupAppListMediator(){
        filteredAppList.value = null
        filteredAppList.addSource(_appList){ al ->
           al?.apply {
               val txt = searchBoxText.value ?: ""
               filteredAppList.value = filterList(this,txt)
           }
        }
        filteredAppList.addSource(searchBoxText){ searchTxt ->
            _appList.value?.apply {
                filteredAppList.value = filterList(this,searchTxt)
            }
        }
    }

    private fun filterList(list:List<AppInfo>,txt:String):List<AppInfo>{
        if(txt.isNotEmpty()){
            val newList = ArrayList<AppInfo>()
            list.forEach { appInfo ->
            val name = appInfo.name
            val sizeDiff = with(name.length - txt.length){
                val res = if(this > 0)this else 0
                res
            }

            if(name.dropLast(sizeDiff).toLowerCase(Locale.ROOT).contains(txt.toLowerCase(Locale.ROOT)))
                newList.add(appInfo)
            }
            return newList
        }
        return list
    }

    fun clearSearch(){
        searchBoxText.value = ""
    }
}