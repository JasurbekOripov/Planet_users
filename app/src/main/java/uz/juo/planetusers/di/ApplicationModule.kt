package uz.juo.planetusers.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.juo.planetusers.retrofit.ApiService
import uz.juo.planetusers.room.AppDataBase
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    fun provideBaseUrl(): String = "https://reqres.in/api/"

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client =
            OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)

        var retrofit = Retrofit.Builder().baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create()).client(client.build())
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            ).build()
        return retrofit
    }

    @Provides
    @Singleton
    fun proviceApiService(): ApiService = providesRetrofit().create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, "planet_users_database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(appDataBase: AppDataBase) = appDataBase.dao()
}