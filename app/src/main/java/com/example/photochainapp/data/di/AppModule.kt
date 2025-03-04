package com.example.photochainapp.data.di

import com.example.photochainapp.data.repository.AuthRepositoryImpl
import android.content.Context
import com.example.photochainapp.data.repository.FriendsRepositoryImpl
import com.example.photochainapp.data.repository.WidgetRepositoryImpl
import com.example.photochainapp.domain.repository.AuthRepository
import com.example.photochainapp.domain.repository.FriendsRepository
import com.example.photochainapp.domain.repository.WidgetRepository
import com.example.photochainapp.domain.usecase.widget.GetWidgetImageUseCase
import com.example.photochainapp.domain.usecase.widget.SaveWidgetImageUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWidgetRepository(@ApplicationContext context: Context): WidgetRepository {
        return WidgetRepositoryImpl(context)
    }

    @Provides
    fun provideSaveWidgetImageUseCase(repository: WidgetRepository): SaveWidgetImageUseCase {
        return SaveWidgetImageUseCase(repository)
    }

    @Provides
    fun provideGetWidgetImageUseCase(repository: WidgetRepository): GetWidgetImageUseCase {
        return GetWidgetImageUseCase(repository)
    }




    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(auth: FirebaseAuth) : AuthRepository {
        return AuthRepositoryImpl(auth)
    }








    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideFriendsRepository(firestore: FirebaseFirestore) : FriendsRepository {
        return FriendsRepositoryImpl(firestore)
    }

}