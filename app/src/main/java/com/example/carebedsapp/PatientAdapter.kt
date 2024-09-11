package com.example.carebedsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carebedsapp.databinding.ItemPatientBinding

class PatientAdapter(private var patients: List<Patient>, private val onPatientClick: (Patient) -> Unit) :
    RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    fun updateData(newItems: List<Patient>) {
        patients= newItems.toMutableList()
        notifyDataSetChanged() // Refresh RecyclerView with new data
    }

    inner class PatientViewHolder(val binding: ItemPatientBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.binding.patientName.text = patient.name
        holder.binding.patientCondition.text = patient.condition

        holder.itemView.setOnClickListener {
            onPatientClick(patient)
        }
    }

    override fun getItemCount(): Int = patients.size
}
