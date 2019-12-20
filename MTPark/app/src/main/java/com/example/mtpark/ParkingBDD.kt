package com.example.mtpark

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.text.DateFormat
import java.util.*


@RealmClass
open class ParkingBDD() : RealmObject(){
    var name: String = ""
    var surname: String = ""
    var free: Int = 0
    var status:String = ""
    var favoris: Boolean = false
    //var total: Int = 0
    var date :String ="Inconnu"


    constructor(Name: String, Status: String, Free: Int, Date: String) : this() {
        name = Name;
        free = Free
        status = Status
        //total = Total
        date = Date
    }

    fun newSurname(newSurname: String){
        surname = newSurname
    }

    fun favOrNot(isFav: Boolean){
        val isFavS = isFav.toString()
        if(isFavS == "true"){
            favoris = true
        }
        else if(isFavS == "false") {
            favoris = false
        }
    }

}