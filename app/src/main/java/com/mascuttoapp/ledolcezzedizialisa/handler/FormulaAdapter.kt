package com.mascuttoapp.ledolcezzedizialisa.handler

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mascuttoapp.ledolcezzedizialisa.bean.Formula


class FormulaHandler{

    companion object {
        fun getFormula(databaseReference: DatabaseReference, number: String) =
            databaseReference.child("formules").child("number").equalTo(number)

        fun getFormulas(databaseReference: DatabaseReference, callback: FirebaseBusCallback): MutableList<Formula> {

            var formulas: ArrayList<Formula>? = arrayListOf()
            databaseReference.child("formules").orderByChild("name")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (postSnapshot in dataSnapshot.children) {
                            val formula = postSnapshot.getValue(Formula::class.java)
                            formulas?.add(formula!!)
                        }
                        callback.onReadCompletedCallback(formulas)
                    }

                })
            return formulas!!
        }
    }

    interface FirebaseBusCallback {
        fun onReadCompletedCallback(value: ArrayList<Formula>?)
    }
}