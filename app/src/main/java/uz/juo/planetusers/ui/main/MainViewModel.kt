package uz.juo.planetusers.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.juo.planetusers.models.Data
import uz.juo.planetusers.retrofit.ApiHelper
import uz.juo.planetusers.room.AppDataBase
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    private val appDataBase: AppDataBase
) : ViewModel() {
    fun users(): LiveData<PagingData<Data>> =
        Pager(PagingConfig(25)) { apiHelper }.flow.cachedIn(viewModelScope).asLiveData()

    fun db() = appDataBase.dao()
}

