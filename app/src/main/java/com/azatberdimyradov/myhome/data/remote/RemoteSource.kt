package com.azatberdimyradov.myhome.data.remote

import com.azatberdimyradov.myhome.data.remote.dto.CameraResponse
import com.azatberdimyradov.myhome.data.remote.dto.DoorResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject

class RemoteSource @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getCameras(): Result<CameraResponse> {
        return try {
            client.get { url(HttpRoutes.CAMERAS_URL) }
        } catch (e: RedirectResponseException) {
            Result.Error<CameraResponse>(e.response.status.description)
        } catch (e: ClientRequestException) {
            Result.Error<CameraResponse>(e.response.status.description)
        } catch (e: ServerResponseException) {
            Result.Error<CameraResponse>(e.response.status.description)
        } catch (e: Exception) {
            Result.Error<CameraResponse>(e.message.toString())
        }
    }

    suspend fun getDoors(): Result<DoorResponse> {
        return try {
            client.get { url(HttpRoutes.DOORS_URL) }
        } catch (e: RedirectResponseException) {
            Result.Error<DoorResponse>(e.response.status.description)
        } catch (e: ClientRequestException) {
            Result.Error<DoorResponse>(e.response.status.description)
        } catch (e: ServerResponseException) {
            Result.Error<DoorResponse>(e.response.status.description)
        } catch (e: Exception) {
            Result.Error<DoorResponse>(e.message.toString())
        }
    }
}