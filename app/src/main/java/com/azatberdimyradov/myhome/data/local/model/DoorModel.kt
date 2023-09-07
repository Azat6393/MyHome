package com.azatberdimyradov.myhome.data.local.model

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class DoorModel : RealmModel {
    @PrimaryKey
    var id: Int? = null
    @Required
    var favorites: Boolean? = null
    @Required
    var name: String? = null
    @Required
    var room: String? = null
    @Required
    var snapshot: String? = null
}