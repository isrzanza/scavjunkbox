package com.dorcohen.scavjunkbox.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dorcohen.scavjunkbox.IRepoAccess
import com.dorcohen.scavjunkbox.data.repository.IRepository

class DefaultViewModelFactory(application: Application? = null) : ViewModelProvider.Factory {
    private val repository: IRepository

    init {
        try{
            val access:IRepoAccess = application as IRepoAccess
            repository = access.getRepository()
        } catch (e:Error){
            throw Error("Application passed to Default view model factory does not implement RepoAccess interface")
        }
    }


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try{
            return modelClass.getConstructor(IRepository::class.java).newInstance(repository)
        }catch (e:Exception){
            throw Exception("Error trying to create viewModel $e")
        }
    }
}