package com.example.mtpark

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import io.realm.Realm
import kotlinx.android.synthetic.main.cell_parking.view.*


class FavorisActivity : AppCompatActivity() {

    var _realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoris)


        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = ParkingFavListAdapter()

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }


    val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Log.d("gg", "gg1")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent);
                    // put your code here
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    // put your code here
                    Log.d("gg", "gg2")
                    val intent = Intent(this, FavorisActivity::class.java)
                    startActivity(intent);

                    return@OnNavigationItemSelectedListener true
                }
//                    R.id.navigation_notifications -> {
//                        Log.d("gg", "gg3")
//
//                        // put your code here
//                        return@OnNavigationItemSelectedListener true
//                    }
            }
            false
        }


    fun onParkingClick(index: Int, context: Context) {

        _realm = Realm.getDefaultInstance()

        val PublicFavList = ParkingFavListAdapter().getFavorisP()
        val parking = PublicFavList[index]

        var parkingAEdit =
            _realm.where(ParkingBDD::class.java).equalTo("name", parking.name).findFirst()


        val intent = Intent(context, ParkingActivity::class.java)
        intent.putExtra("name", parking.name).putExtra("status", parking.status)
            .putExtra("free", parking.free).putExtra("surname", parkingAEdit?.surname).putExtra("favoris", parkingAEdit?.favoris).putExtra("date", parkingAEdit?.date)
        startActivity(intent);

    }




    inner class ParkingFavListAdapter : RecyclerView.Adapter<ParkingFavListAdapter.ParkingCellHolder>() {

        val favParkingList = getFavorisP()


        fun getFavorisP():List<ParkingBDD>{
            var _realm = Realm.getDefaultInstance()

            val data = _realm.where(ParkingBDD::class.java).equalTo("favoris", true).findAll().toList()
            if (data.isEmpty()){
                Toast.makeText(this@FavorisActivity, "Aucun favoris", Toast.LENGTH_LONG).show()
            }
            return data
        }

        inner class ParkingCellHolder(view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener {

            override fun onClick(p0: View?) {
                if (p0 != null) {
                    onParkingClick(adapterPosition, p0.context)
                }
            }

            val statusLabel = view.lbl_parkingStatus
            val nameLabel = view.lbl_parkingName
            val freeLabel = view.lbl_parkingFree
            val checkBox = view.checkBoxFav
            init {
                view.setOnClickListener(this)
            }

            fun displayerParking(parking: ParkingBDD) {
                if(parking.surname !=""){
                    nameLabel.text=parking.surname
                }else{
                    nameLabel.text = parking.name
                }
                statusLabel.text = parking.status
                freeLabel.text=parking.free.toString()
                checkBox.isChecked = true

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingCellHolder {
            Log.i("BRITTO", "onCreateViewHolder")
            // 1 creer la vue avec mise en page
            val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_parking, parent, false)

            // 2 créer un holder associé à la vue
            val holder = ParkingCellHolder(view)

            // 3 retourner le holder
            return holder
        }

        override fun getItemCount(): Int {
            return favParkingList.count()
        }

        override fun onBindViewHolder(holder: ParkingCellHolder, position: Int) {
            Log.i("BRITTO", "onBindViewHolder $position")
            // 1 charger le parking 'position'
            //val myParking = Parking("Bob ${position}", position * position * 100)

            // 2 envoyer le parking dans le holder
            holder.displayerParking(favParkingList[position])
        }
    }
}
