package com.mascuttoapp.ledolcezzedizialisa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mascuttoapp.ledolcezzedizialisa.R
import com.mascuttoapp.ledolcezzedizialisa.bean.Formula
import com.mascuttoapp.ledolcezzedizialisa.util.Utils

class FormulaAdapter( var myDataset: MutableList<Formula>, val listener: (Formula) -> Unit) : RecyclerView.Adapter<FormulaAdapter.FormulaViewHolder>() {


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FormulaViewHolder {
        // Create a new view, which defines the UI of the list item
        val card: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.formula_card, viewGroup, false)
        return FormulaViewHolder(card)
    }

    override fun onBindViewHolder(holder: FormulaViewHolder, position: Int) {
        holder.updateUI(myDataset[position])
        holder.itemView.setOnClickListener {
            listener( myDataset[position])

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class FormulaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var icon: ImageView = view.findViewById<View>(R.id.icon_cake) as ImageView
        var description: TextView? = view.findViewById<View>(R.id.description) as TextView
        var duration: TextView? = view.findViewById<View>(R.id.indexStep) as TextView
        var progress: ProgressBar = view.findViewById<View>(R.id.progressBar) as ProgressBar
        var level: TextView? = view.findViewById<View>(R.id.level) as TextView

        fun updateUI(formula: Formula){
            description?.text = formula.name
            Utils.doInBackground(icon, progress,formula.icon)
            duration?.text = "circa ".plus(formula.duration).plus(" minuti")
            level?.text = "difficoltà ".plus(formula.level)
        }
    }
}
