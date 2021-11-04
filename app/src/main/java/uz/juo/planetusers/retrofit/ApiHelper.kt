package uz.juo.planetusers.retrofit

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.juo.planetusers.models.Data
import uz.juo.planetusers.models.User
import uz.juo.planetusers.room.AppDataBase
import uz.juo.planetusers.room.entity.UserEntity
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHelper @Inject constructor(var apiService: ApiService, var database: AppDataBase) :
    PagingSource<Int, Data>() {
    var liveData = ArrayList<Data>()
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            var page = params.key ?: 1
            for (i in 0 until 4) {
                var users = apiService.getUsers(i)
                if (users.isSuccessful) {
                    users.body()?.data?.let { liveData.addAll(it) }
                }
            }
            if (liveData.size > 7) {
                LoadResult.Page(liveData, page - 1, page + 1)
            } else {
                LoadResult.Page(liveData, null, page + 1)
            }
        } catch (e: Exception) {
            return LoadResult.Page(emptyList(), null, null)
        }
    }

}