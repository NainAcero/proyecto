package com.nain.ejercicio.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.nain.ejercicio.R
import com.nain.ejercicio.models.Persona
import kotlinx.android.synthetic.main.item_appointment.view.*

class PersonaAdapter: RecyclerView.Adapter<PersonaAdapter.ViewHolder>(){
    var appointments = ArrayList<Persona>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(appointment: Persona) = with(itemView) {
            tvUsuarioId.text = "Usuario #" + appointment.id.toString()
            tvUsuarioName.text = appointment.nombre + " " + appointment.apellido
            tvSueldo.text = appointment.sueldo.toString()
            tvStatus.text = "ACTIVO"

            ibExpand.setOnClickListener{
                TransitionManager.beginDelayedTransition(parent as ViewGroup, AutoTransition() )

                if(linearLayoutDetails.visibility == View.VISIBLE) {
                    linearLayoutDetails.visibility = View.GONE
                    ibExpand.setImageResource(R.drawable.ic_expand_more)
                } else {
                    linearLayoutDetails.visibility = View.VISIBLE
                    ibExpand.setImageResource(R.drawable.ic_expand_less)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_appointment,
                parent,
                false
            )
        )
    }

    // binds data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.bind(appointment)
    }

    // Number of element
    override fun getItemCount(): Int {
        return appointments.size
    }
}