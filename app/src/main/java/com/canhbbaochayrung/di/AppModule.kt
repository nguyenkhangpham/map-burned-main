package com.canhbbaochayrung.di

import com.canhbbaochayrung.services.NasaService
import com.canhbbaochayrung.utils.CSVInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNasaService(): NasaService{
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val logger= HttpLoggingInterceptor()
        logger.level =  HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
        client.addInterceptor(logger)
        client.addInterceptor(CSVInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://firms.modaps.eosdis.nasa.gov/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client.build())
            .build()

        return retrofit.create(NasaService::class.java)
    }
}