package com.safeway.nycschools.di

import com.safeway.nycschools.MockServer
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class MockAppModule : AppModule() {
    override fun baseUrl() = MockServer.server.url("http://localhost/")
}