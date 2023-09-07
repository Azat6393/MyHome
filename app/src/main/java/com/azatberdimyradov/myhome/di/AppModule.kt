package com.azatberdimyradov.myhome.di

import android.content.Context
import com.azatberdimyradov.myhome.data.local.LocalDatabase
import com.azatberdimyradov.myhome.data.remote.RemoteSource
import com.azatberdimyradov.myhome.data.repository.MyHomeRepositoryImpl
import com.azatberdimyradov.myhome.domain.repository.MyHomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRealm(
        @ApplicationContext context: Context
    ): Realm {
        Realm.init(context)
        val configuration = RealmConfiguration.Builder()
            .name("my_home.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(configuration)
        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    fun providedHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(realm: Realm): LocalDatabase {
        return LocalDatabase(realm)
    }

    @Provides
    @Singleton
    fun provideMyHomeRepository(
        remoteSource: RemoteSource,
        localDatabase: LocalDatabase
    ): MyHomeRepository {
        return MyHomeRepositoryImpl(remoteSource, localDatabase)
    }

    @Provides
    @Singleton
    fun provideRemoteSource(client: HttpClient): RemoteSource {
        return RemoteSource(client)
    }
}