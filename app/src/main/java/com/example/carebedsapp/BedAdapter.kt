package com.example.carebedsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carebedsapp.databinding.ItemBedBinding

class BedAdapter(private val beds: List<Bed>, private val onBedClick: (Bed) -> Unit) :
    RecyclerView.Adapter<BedAdapter.BedViewHolder>() {

    inner class BedViewHolder(val binding: ItemBedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BedViewHolder {
        val binding = ItemBedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BedViewHolder, position: Int) {
        val bed = beds[position]
        holder.binding.bedId.text = bed.id.toString()
        holder.binding.bedStatus.text = bed.status

        holder.itemView.setOnClickListener {
            onBedClick(bed)
        }
    }

    override fun getItemCount(): Int = beds.size
}
