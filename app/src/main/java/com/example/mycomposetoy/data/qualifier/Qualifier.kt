package com.example.mycomposetoy.data.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeaderInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthTokenInterceptor // 인증이 필요한 API

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlainClient // 토큰 갱신용 API