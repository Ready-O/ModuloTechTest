package com.reda.modulotechtest.repository

import com.reda.modulotechtest.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(): Flow<Result<User>>
    suspend fun updateUser(user: User)
}