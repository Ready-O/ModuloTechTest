package com.reda.modulotechtest.repository

import com.reda.modulotechtest.model.User
import com.reda.modulotechtest.network.RemoteDataSource
import com.reda.modulotechtest.persistence.dao.UserDao
import com.reda.modulotechtest.persistence.model.toUser
import com.reda.modulotechtest.persistence.model.toUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val remoteDataSource: RemoteDataSource
): UserRepository{

    override suspend fun getUser(): Flow<Result<User>> = userDao.getUser().map { user ->
        if (user == null){
            remoteDataSource.fetchUser().onSuccess {
                userDao.insertUser(it.toUserEntity())
            }
        }
        else{
            Result.success(user.toUser())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user.toUserEntity())
    }
}