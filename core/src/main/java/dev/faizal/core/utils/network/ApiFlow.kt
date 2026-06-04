package dev.faizal.core.utils.network


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

fun <T> apiFlow(call : suspend () -> T): Flow<Resource<T>> = flow{
    emit(Resource.Loading())
    try {
        emit(Resource.Success(call()))
    } catch (e: HttpException) {
        emit(Resource.Error(e))
    } catch (e: IOException) {
        emit(Resource.Error(e))
    } catch (e: Exception) {
        emit(Resource.Error(e))
    }
}