package com.nain.ejercicio.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mancj.materialsearchbar.MaterialSearchBar
import com.nain.ejercicio.R
import com.nain.ejercicio.io.ApiService
import com.nain.ejercicio.models.Persona
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() , MaterialSearchBar.OnSearchActionListener{

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val appointmentAdapter = PersonaAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvAppointments.layoutManager = LinearLayoutManager(this)
        rvAppointments.adapter = appointmentAdapter

        searchBar.setOnSearchActionListener(this);

        getPersonas()
    }

    private fun getPersonas(){
        val call = apiService.getPersona()
        call.enqueue(object: Callback<ArrayList<Persona>> {
            override fun onFailure(call: Call<ArrayList<Persona>>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ArrayList<Persona>>,
                response: Response<ArrayList<Persona>>
            ) {
                if(response.isSuccessful) {
                    // appointments es un Arraylist<>
//                    val appointments = response.body()
                    response.body()?.let {
                        appointmentAdapter.appointments = it
                        toast(it.toString())
                        appointmentAdapter.notifyDataSetChanged() // notiificar a la interfaz de los cambios
                    }
                }
            }

        })
    }

    private fun searchByTitle(title: String) {
        val call = apiService.getPersonaFindNombre(title)
        call.enqueue(object: Callback<ArrayList<Persona>> {
            override fun onFailure(call: Call<ArrayList<Persona>>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ArrayList<Persona>>,
                response: Response<ArrayList<Persona>>
            ) {
                if(response.isSuccessful) {
                    // appointments es un Arraylist<>
//                    val appointments = response.body()
                    response.body()?.let {
                        appointmentAdapter.appointments = it
                        toast(it.toString())
                        appointmentAdapter.notifyDataSetChanged() // notiificar a la interfaz de los cambios
                    }
                }
            }

        })
    }

    override fun onButtonClicked(buttonCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        if (!enabled) {
            getPersonas()
        }
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        searchByTitle(text.toString())
    }
}