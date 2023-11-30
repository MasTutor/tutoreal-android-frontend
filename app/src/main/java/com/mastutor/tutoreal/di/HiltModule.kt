package com.mastutor.tutoreal.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mastutor.tutoreal.data.preferences.SessionPreferences
import com.mastutor.tutoreal.data.remote.TutorealApiConfig
import com.mastutor.tutoreal.data.remote.ImgurApiConfig
import com.mastutor.tutoreal.data.remote.ImgurApiService
import com.mastutor.tutoreal.data.remote.TutorealApiService
import com.mastutor.tutoreal.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "session")
@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun provideTutorealApiService(): TutorealApiService = TutorealApiConfig.getApiService()

    @Provides
    fun provideImgurApiService(): ImgurApiService = ImgurApiConfig.getApiService()

    @Provides
    @Singleton
    fun provideRepository(tutorealApiService: TutorealApiService,
                          imgurApiService: ImgurApiService,
                          preferences: SessionPreferences,
                          @ApplicationContext context: Context
    ) = Repository(tutorealApiService, preferences, imgurApiService, context)

    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences> = context.datastore

    @Provides
    @Singleton
    fun provideSessionPreferences(dataStore: DataStore<Preferences>) = SessionPreferences(dataStore)
}