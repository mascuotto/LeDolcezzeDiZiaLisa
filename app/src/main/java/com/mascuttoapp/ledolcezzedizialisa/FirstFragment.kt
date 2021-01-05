package com.mascuttoapp.ledolcezzedizialisa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mascuttoapp.ledolcezzedizialisa.adapter.FormulaAdapter
import com.mascuttoapp.ledolcezzedizialisa.bean.Formula
import com.mascuttoapp.ledolcezzedizialisa.handler.FormulaHandler
import java.util.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var formulaDataset: ArrayList<Formula>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.formulas)
        viewManager = LinearLayoutManager(activity)
        recyclerView.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewAdapter = FormulaAdapter(FormulaHandler.getFormulas(
                      database, object : FormulaHandler.FirebaseBusCallback {

                    override fun onReadCompletedCallback(value: ArrayList<Formula>?) {
                        if (value != null) {
                            formulaDataset = value
                            viewAdapter.notifyDataSetChanged()
                        }
                    }
                })
            ) { position ->
                var bundle = bundleOf("formulaSelected" to position)
                bundle?.putSerializable("formulaSelected", position)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
}
}