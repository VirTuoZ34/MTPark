package com.example.mtpark

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cell_parking.view.*
import javax.xml.parsers.DocumentBuilderFactory
import java.io.IOException
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_parking.*
import kotlinx.android.synthetic.main.cell_parking.*
import kotlinx.android.synthetic.main.cell_parking.view.checkBoxFav
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.File
import java.io.FileOutputStream
import java.io.StringReader
import java.net.URL
import javax.xml.parsers.ParserConfigurationException


class MainActivity : AppCompatActivity() {
    lateinit var _realm: Realm

    var empDataHashMap = HashMap<String, String>()
    var empList: ArrayList<HashMap<String, String>> = ArrayList()
    var parklist: ArrayList<HashMap<String, String>> = ArrayList()
    var parkingListe = mutableListOf<ParkingBDD>()
    var requestQueue: RequestQueue? = null

    var myList = mutableListOf<String>()
    var dlCount =0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)


        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        startDownloadQueue()
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
                R.id.navigation_notifications -> {
                    Log.d("gg", "gg3")

                    // put your code here
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    // function to return node value
    protected fun getNodeValue(tag: String, element: Element): String {
        val nodeList = element.getElementsByTagName(tag)
        val node = nodeList.item(0)
        if (node != null) {
            if (node.hasChildNodes()) {
                val child = node.getFirstChild()
                while (child != null) {
                    if (child.nodeType === Node.TEXT_NODE) {
                        return child.getNodeValue()
                    }
                }
            }
        }

        return ""
    }


    inner class ParkingListAdapter : RecyclerView.Adapter<ParkingListAdapter.ParkingCellHolder>() {

        var parkingList = listOf(

            ParkingBDD(
                parklist[0]["Name"].toString(),
                parklist[0]["Status"].toString(),
                parklist[0]["Free"].toString().toInt(),
                parklist[0]["Date"].toString()


                ),
            ParkingBDD(
                parklist[1]["Name"].toString(),
                parklist[1]["Status"].toString(),
                parklist[1]["Free"].toString().toInt(),
                parklist[1]["Date"].toString()

            ),
            ParkingBDD(
                parklist[2]["Name"].toString(),
                parklist[2]["Status"].toString(),
                parklist[2]["Free"].toString().toInt(),
                parklist[2]["Date"].toString()

            ),
            ParkingBDD(
                parklist[3]["Name"].toString(),
                parklist[3]["Status"].toString(),
                parklist[3]["Free"].toString().toInt(),
                parklist[3]["Date"].toString()

            ),
            ParkingBDD(
                parklist[4]["Name"].toString(),
                parklist[4]["Status"].toString(),
                parklist[4]["Free"].toString().toInt(),
                parklist[4]["Date"].toString()

            ),
            ParkingBDD(
                parklist[5]["Name"].toString(),
                parklist[5]["Status"].toString(),
                parklist[5]["Free"].toString().toInt(),
                parklist[5]["Date"].toString()

            ),
            ParkingBDD(
                parklist[6]["Name"].toString(),
                parklist[6]["Status"].toString(),
                parklist[6]["Free"].toString().toInt(),
                parklist[6]["Date"].toString()

            ),
            ParkingBDD(
                parklist[7]["Name"].toString(),
                parklist[7]["Status"].toString(),
                parklist[7]["Free"].toString().toInt(),
                parklist[7]["Date"].toString()


            ),
            ParkingBDD(
                parklist[8]["Name"].toString(),
                parklist[8]["Status"].toString(),
                parklist[8]["Free"].toString().toInt(),
                parklist[8]["Date"].toString()

            ),
            ParkingBDD(
                parklist[9]["Name"].toString(),
                parklist[9]["Status"].toString(),
                parklist[9]["Free"].toString().toInt(),
                parklist[9]["Date"].toString()

            ),
            ParkingBDD(
                parklist[10]["Name"].toString(),
                parklist[10]["Status"].toString(),
                parklist[10]["Free"].toString().toInt(),
                parklist[10]["Date"].toString()


            ),
            ParkingBDD(
                parklist[11]["Name"].toString(),
                parklist[11]["Status"].toString(),
                parklist[11]["Free"].toString().toInt(),
                parklist[11]["Date"].toString()

            ),
            ParkingBDD(
                parklist[12]["Name"].toString(),
                parklist[12]["Status"].toString(),
                parklist[12]["Free"].toString().toInt(),
                parklist[12]["Date"].toString()

            ),
            ParkingBDD(
                parklist[13]["Name"].toString(),
                parklist[13]["Status"].toString(),
                parklist[13]["Free"].toString().toInt(),
                parklist[13]["Date"].toString()


            ),
            ParkingBDD(
                parklist[14]["Name"].toString(),
                parklist[14]["Status"].toString(),
                parklist[14]["Free"].toString().toInt(),
                parklist[14]["Date"].toString()

            ),
            ParkingBDD(
                parklist[15]["Name"].toString(),
                parklist[15]["Status"].toString(),
                parklist[15]["Free"].toString().toInt(),
                parklist[15]["Date"].toString()

            ),
            ParkingBDD(
                parklist[16]["Name"].toString(),
                parklist[16]["Status"].toString(),
                parklist[16]["Free"].toString().toInt(),
                parklist[16]["Date"].toString()

            ),
            ParkingBDD(
                parklist[17]["Name"].toString(),
                parklist[17]["Status"].toString(),
                parklist[17]["Free"].toString().toInt(),
                parklist[17]["Date"].toString()

            ),
            ParkingBDD(
                parklist[18]["Name"].toString(),
                parklist[18]["Status"].toString(),
                parklist[18]["Free"].toString().toInt(),
                parklist[18]["Date"].toString()

            ),
            ParkingBDD(
                parklist[19]["Name"].toString(),
                parklist[19]["Status"].toString(),
                parklist[19]["Free"].toString().toInt(),
                parklist[19]["Date"].toString()

            ),
            ParkingBDD(
                parklist[20]["Name"].toString(),
                parklist[20]["Status"].toString(),
                parklist[20]["Free"].toString().toInt(),
                parklist[20]["Date"].toString()

            )

        )

        fun transfert() {
            parkingList.forEach() {
                parkingListe.add(it)
            }
        }

        fun onParkingClick(index: Int, context: Context) {

            _realm = Realm.getDefaultInstance()


            val parking = parkingListe[index]
            Toast.makeText(context, parking.name, Toast.LENGTH_SHORT).show()

            var parkingAEdit =
                _realm.where(ParkingBDD::class.java).equalTo("name", parking.name).findFirst()


            val intent = Intent(context, ParkingActivity::class.java)
            intent.putExtra("name", parking.name).putExtra("status", parking.status)
                .putExtra("free", parking.free).putExtra("surname", parkingAEdit?.surname).putExtra("favoris", parkingAEdit?.favoris).putExtra("date", parkingAEdit?.date)
            startActivity(intent);

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

                _realm = Realm.getDefaultInstance()

                val parking1 =
                    _realm.where(ParkingBDD::class.java).equalTo("name", parking.name).findFirst()
                if (parking1 != null) {
                    if (parking1.surname != "") {
                        nameLabel.text = parking1.surname
                    } else {
                        nameLabel.text = parking.name
                    }
                }
                statusLabel.text = parking.status
                freeLabel.text = "${parking.free.toString()} places libres"


                _realm = Realm.getDefaultInstance()

                val p = _realm.where(ParkingBDD::class.java).equalTo("name",parking.name).findFirst()
                val fav = p?.favoris.toString()
                if(fav == "true"){
                        checkBox.isChecked=true

                    }else if(fav == "false"){
                        checkBox.isChecked=false
                }

            }

        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingCellHolder {
            // 1 creer la vue avec mise en page
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.cell_parking, parent, false)

            // 2 créer un holder associé à la vue
            val holder = ParkingCellHolder(view)

            // 3 retourner le holder
            return holder
        }

        override fun getItemCount(): Int {
            return parkingList.count()
        }

        override fun onBindViewHolder(holder: ParkingCellHolder, position: Int) {
            Log.i("BRITTO", "onBindViewHolder $position")
            // 1 charger le player 'position'
            //val myParking = Parking("Bob ${position}", position * position * 100)

            // 2 envoyer le player dans le holder
            holder.displayerParking(parkingList[position])
        }
    }

    fun loadValuesString(file: String) {
        try {

            val issou = InputSource(StringReader(file));

            val istream = issou
            val builderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder = builderFactory.newDocumentBuilder()
            val doc = docBuilder.parse(istream)
            //reading the tag "employee" of empdetail file
            val nList = doc.getElementsByTagName("park")
            for (i in 0 until nList.getLength()) {
                if (nList.item(0).getNodeType().equals(Node.ELEMENT_NODE)) {
                    //creating instance of HashMap to put the data of node value
                    empDataHashMap = HashMap()
                    val element = nList.item(i) as Element
                    empDataHashMap.put("Name", getNodeValue("Name", element))
                    empDataHashMap.put("Status", getNodeValue("Status", element))
                    empDataHashMap.put("Free", getNodeValue("Free", element))
                    empDataHashMap.put("Date", getNodeValue("DateTime", element))
                    //empDataHashMap.put("Total", getNodeValue("Total", element))



                    parklist.add(empDataHashMap)
                    empList.add(empDataHashMap)

                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
        } catch (e: SAXException) {
            e.printStackTrace()
        }
    }


    fun setDB(liste: MutableList<ParkingBDD>) {

        _realm.beginTransaction()
        liste.forEach {
            val parkbdd = ParkingBDD()
            parkbdd.name = it.name
            parkbdd.free = it.free
            parkbdd.status = it.status
            parkbdd.date = it.date
            //parkbdd.total = it.total

            _realm.copyToRealm(parkbdd)
        }
        _realm.commitTransaction()
    }


    fun refreshDB() {
        _realm = Realm.getDefaultInstance()
        _realm.beginTransaction()
        val p = _realm.where(ParkingBDD::class.java).findAll()
        p.forEachIndexed { index, element ->
            val h: ParkingBDD? = parkingListe.find { it.name == element.name }
            if (h != null) {
                element.free = h.free
                element.status = h.status
                element.date = h.date


            }
        }
        _realm.commitTransaction()

    }

    fun refreshParkingListe() {
        _realm = Realm.getDefaultInstance()
        parkingListe.forEach() {
            val p = _realm.where(ParkingBDD::class.java).equalTo("name", it.name).findFirst()
            if (p != null) {
                it.free = p.free
                it.date = p.date

            }
        }
    }


    fun wasFilled(): Boolean {
        _realm = Realm.getDefaultInstance()
        val data = _realm.where(ParkingBDD::class.java).findAll()
        if (data.size != 0) {
            return true
        } else {
            return false
        }
    }


    fun startDownloadQueue(){
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_ANTI.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_ARCT.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_COME.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_CORU.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_EURO.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_FOCH.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_GAMB.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_GARE.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_TRIA.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_PITO.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_CIRCE.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_GARC.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_MOSS.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_SABI.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_SABL.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_STJ_SJLC.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_MEDC.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_OCCI.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_CAS_VICA.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_GA109.xml")
        launchDownloadRequest("E:\\EPSI\\B2\\Android\\MTPARK\\MTPark\\app\\src\\main\\assets\\file.xml","https://data.montpellier3m.fr/sites/default/files/ressources/FR_MTP_GA250.xml")
    }

    fun launchDownloadRequest(path: String, link: String){
        val request = StringRequest(Request.Method.GET, link, Response.Listener {
            val p = it
            myList.add(p)
            dlCount++
            if(dlCount == 21){
                dlComplete()
            }
        }, Response.ErrorListener {
            Log.i("xml", "error")
        })
        requestQueue?.add(request)
        //createXMLfile(file, "MONXML")
    }


    fun dlComplete(){
        Log.i("dl1", myList.toString())

        myList.forEach(){
            loadValuesString(it)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = ParkingListAdapter()

        ParkingListAdapter().transfert()

        var bddOk = wasFilled()
        if (bddOk == false) {
            setDB(parkingListe)
        }

        //refreshParkingListe()
        refreshDB()
        _realm = Realm.getDefaultInstance()
    }


}
