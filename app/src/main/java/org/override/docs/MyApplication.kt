package org.override.docs

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.override.docs.data.auth.api.plataform.FirebaseAuthManager
import org.override.docs.data.auth.impl.firebase.FirebaseAuthManagerImpl
import org.override.docs.data.session.api.SessionRepository
import org.override.docs.data.session.impl.datastore.SessionDataStoreRepositoryImpl
import org.override.docs.domain.auth.usecase.CreateUserWithEmailAndPasswordUseCase
import org.override.docs.domain.auth.usecase.DeleteCurrentUserUseCase
import org.override.docs.domain.auth.usecase.GetCurrentUserUseCase
import org.override.docs.domain.auth.usecase.ReauthenticateUseCase
import org.override.docs.domain.auth.usecase.SendPasswordResetEmailUseCase
import org.override.docs.domain.auth.usecase.SignInWithCredentialUseCase
import org.override.docs.domain.auth.usecase.SignInWithEmailAndPasswordUseCase
import org.override.docs.domain.auth.usecase.SignOutUseCase
import org.override.docs.domain.session.usecase.ClearUserSessionUseCase
import org.override.docs.domain.session.usecase.GetCurrentUserIdUseCase
import org.override.docs.domain.session.usecase.IsUserLoggedInUseCase
import org.override.docs.domain.session.usecase.SaveUserSessionUseCase
import org.override.docs.presentation.features.login.LoginViewModel

private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")
private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_prefs")

class MyApplication : Application(), KoinComponent {
    object DiQualifiers {
        val SESSION_DATASTORE = named("SessionDataStore")
        val SETTINGS_DATASTORE = named("SettingsDataStore")
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            allowOverride(true)
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                viewModelModule,
                sessionRepositoryModule,
                firebaseRepositoryModule,
                domainModule
            )
        }
    }

    val viewModelModule: Module
        get() = module {
            viewModelOf(::LoginViewModel)
        }

    val sessionRepositoryModule: Module
        get() = module {
            single<SessionRepository> {
                SessionDataStoreRepositoryImpl(get(qualifier = DiQualifiers.SESSION_DATASTORE))
            }
            single(qualifier = DiQualifiers.SESSION_DATASTORE) {
                androidContext().sessionDataStore
            }
            single(qualifier = DiQualifiers.SETTINGS_DATASTORE) {
                androidContext().settingsDataStore
            }
        }

    val firebaseRepositoryModule: Module
        get() = module {
            single<FirebaseAuth> { FirebaseAuth.getInstance() }
        }

    val domainModule: Module
        get() = module {
            // Firebase auth manager implementation
            singleOf(::FirebaseAuthManagerImpl).bind<FirebaseAuthManager>()

            // Auth use cases
            singleOf(::CreateUserWithEmailAndPasswordUseCase)
            singleOf(::SignInWithEmailAndPasswordUseCase)
            singleOf(::SignInWithCredentialUseCase)
            singleOf(::SendPasswordResetEmailUseCase)
            singleOf(::GetCurrentUserUseCase)
            singleOf(::SignOutUseCase)
            singleOf(::DeleteCurrentUserUseCase)
            singleOf(::ReauthenticateUseCase)

            // Session use cases (depend on SessionRepository)
            singleOf(::IsUserLoggedInUseCase)
            singleOf(::SaveUserSessionUseCase)
            singleOf(::ClearUserSessionUseCase)
            singleOf(::GetCurrentUserIdUseCase)
        }
}