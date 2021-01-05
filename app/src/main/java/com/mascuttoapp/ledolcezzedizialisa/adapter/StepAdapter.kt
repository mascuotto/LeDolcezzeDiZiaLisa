package com.mascuttoapp.ledolcezzedizialisa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mascuttoapp.ledolcezzedizialisa.R

class StepAdapter(var myDataset: MutableList<String>) : RecyclerView.Adapter<StepAdapter.StepViewHolder>() {


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StepViewHolder {
        // Create a new view, which defines the UI of the list item
        val card: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.step_card, viewGroup, false)
        return StepViewHolder(card)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.updateUI(myDataset[position], position+1)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class StepViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var description: TextView? = view.findViewById<View>(R.id.descriptionStep) as TextView
        var index: TextView? = view.findViewById<View>(R.id.indexStep) as TextView

        fun updateUI(step: String, position: Int){
            index?.text = position.toString()
            description?.text = step
        }
    }
}