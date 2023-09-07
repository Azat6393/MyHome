package com.azatberdimyradov.myhome.data.local

import androidx.lifecycle.MutableLiveData
import com.azatberdimyradov.myhome.data.local.model.CameraModel
import com.azatberdimyradov.myhome.data.local.model.DoorModel
import com.azatberdimyradov.myhome.data.remote.dto.CameraDto
import com.azatberdimyradov.myhome.data.remote.dto.DoorDataDto
import io.realm.Realm
import java.util.Random
import javax.inject.Inject

class LocalDatabase @Inject constructor(
    private val realm: Realm
) {

    fun addCamera(cameraDto: CameraDto) {
        realm.executeTransaction { r: Realm ->
            val camera = r.createObject(CameraModel::class.java,cameraDto.id ?: Random().nextInt())
            camera.name = cameraDto.name ?: ""
            camera.rec = cameraDto.rec ?: false
            camera.room = cameraDto.room ?: ""
            camera.favorites = cameraDto.favorites ?: false
            camera.snapshot = cameraDto.snapshot ?: ""
            realm.insertOrUpdate(camera)
        }
    }

    fun addDoor(doorDto: DoorDataDto) {
        realm.executeTransaction { r: Realm ->
            val door = r.createObject(DoorModel::class.java, doorDto.id ?: Random().nextInt())
            door.room = doorDto.room ?: ""
            door.name = doorDto.name ?: ""
            door.favorites = doorDto.favorites ?: false
            door.snapshot = doorDto.snapshot ?: ""
            realm.insertOrUpdate(door)
        }
    }


    fun deleteAllCameras() {
        realm.executeTransaction { r: Realm ->
            r.delete(CameraModel::class.java)
        }
    }

    fun deleteAllDoors() {
        realm.executeTransaction { r: Realm ->
            r.delete(DoorModel::class.java)
        }
    }

    fun getAllCameras(): MutableLiveData<List<CameraModel>> {
        val list = MutableLiveData<List<CameraModel>>()
        val cameras = realm.where(CameraModel::class.java).findAll()
        list.value = cameras?.subList(0, cameras.size)
        return list
    }

    fun getAllDoors(): MutableLiveData<List<DoorModel>> {
        val list = MutableLiveData<List<DoorModel>>()
        val doors = realm.where(DoorModel::class.java).findAll()
        list.value = doors?.subList(0, doors.size)
        return list
    }

    fun databaseIsEmpty(): Boolean{
        return realm.isEmpty
    }
}