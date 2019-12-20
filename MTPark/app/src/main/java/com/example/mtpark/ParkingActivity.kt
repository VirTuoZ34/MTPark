package com.example.mtpark

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_parking.*

class ParkingActivity : AppCompatActivity() {
    lateinit var _realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking)

        val surnom = intent.getStringExtra("surname").toString()
        val favoris = intent.getBooleanExtra("favoris", false).toString()

        if(favoris == "true"){
            switchFav!!.isChecked=true

        }else if(favoris == "false"){
            switchFav!!.isChecked=false

        }

        if( surnom != ""){
            surname.text ="${getString(R.string.surname)} ${intent.getStringExtra("surname")}"


        } else{
            surname.text = "Pas de surnom"

        }
        name.text = "${intent.getStringExtra("name")}"
        status.text = "${getString(R.string.status)} ${intent.getStringExtra("status")}"
        free.text = "${getString(R.string.free)} ${intent.getIntExtra("free", 0).toString()}"
        date.text = "${getString(R.string.maj)} ${intent.getStringExtra("date")}"


        okEdit.setOnClickListener { editButtonTouched() }

        switchFav.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                changeFavoris(true, name.text.toString())
            } else {
                // The switch is disabled
                changeFavoris(false, name.text.toString())

            }
        }

    }




    fun editButtonTouched() {
        val userInput = editParking.text.toString()
        if (userInput != "") {
            saveNameDB(userInput, name.text.toString())
            surname.text = userInput
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent);
    }

    fun saveNameDB(newSurname: String, name: String) {
        _realm = Realm.getDefaultInstance()
        val list = _realm.where(ParkingBDD::class.java).findAll()
        var parkingAEdit = _realm.where(ParkingBDD::class.java).equalTo("name",name).findFirst()
            _realm.beginTransaction()
        if (parkingAEdit != null) {
            parkingAEdit.newSurname(newSurname)
        }
            _realm.commitTransaction()

    }

    fun changeFavoris(status: Boolean, name: String){

        _realm = Realm.getDefaultInstance()
        val list = _realm.where(ParkingBDD::class.java).findAll()
        var parkingAEdit = _realm.where(ParkingBDD::class.java).equalTo("name",name).findFirst()
        _realm.beginTransaction()
        if (parkingAEdit != null) {
            parkingAEdit.favOrNot(status)
        }
        _realm.commitTransaction()
        Log.i("fav", parkingAEdit?.favoris.toString())

    }
}