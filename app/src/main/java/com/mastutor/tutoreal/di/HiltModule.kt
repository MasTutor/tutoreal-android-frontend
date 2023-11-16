package com.mastutor.tutoreal.di

import com.mastutor.tutoreal.data.remote.TutorealApiConfig
import com.mastutor.tutoreal.data.remote.TutorealApiService
import com.mastutor.tutoreal.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun provideTutorealApiService(): TutorealApiService = TutorealApiConfig.getApiService()

    @Provides
    @Singleton
    fun provideRepository(tutorealApiService: TutorealApiService) = Repository(tutorealApiService)
}