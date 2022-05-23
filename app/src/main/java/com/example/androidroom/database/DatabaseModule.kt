package com.example.androidroom.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideMarker(markerDatabase: MarkerDatabase): MarkerDAO {
        return markerDatabase.markerDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MarkerDatabase {
        return Room.databaseBuilder(
            appContext,
            MarkerDatabase::class.java,
            "RssReader"
        ).fallbackToDestructiveMigration().build()
    }
}