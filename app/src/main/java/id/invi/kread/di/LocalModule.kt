package id.invi.kread.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.invi.kread.data.local.datastore.SessionManager
import id.invi.kread.data.local.room.AppRoomDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppRoomDatabase {
        return Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java,
            "kread_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHabitTrackingDao(appRoomDatabase: AppRoomDatabase) = appRoomDatabase.habitTrackingDao()

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }
}
