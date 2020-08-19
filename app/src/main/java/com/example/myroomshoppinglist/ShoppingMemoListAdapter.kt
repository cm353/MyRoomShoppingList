package com.example.myroomshoppinglist

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myroomshoppinglist.database.ShoppingMemo


class ShoppingMemoListAdapter : RecyclerView.Adapter<ShoppingMemoListAdapter.ShoppingMemoViewHolder>() {

    private var shoppingMemos : List<ShoppingMemo> = ArrayList()

// region clicklistener für RecyclerView erzeugen
    private var onItemClickListener : OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(memo: ShoppingMemo)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
//endregion

    // Layout des Recyclerviews erzeugen: Layoutdatei = Layout des einzelnen eintrages
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingMemoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_multiple_choice, parent,false)
        return ShoppingMemoViewHolder(itemView)
    }

    // Layoutelemente mit den eingangsdaten verbinden
    override fun onBindViewHolder(holder: ShoppingMemoViewHolder, position: Int) {
        val currrentShoppingMemo = shoppingMemos.get(position)
        val textView = holder.itemView as TextView
        textView.text = currrentShoppingMemo.toString()

// Durchstreich-Funktionalität
        if (currrentShoppingMemo.isChecked) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textView.setTextColor(Color.rgb(175, 175, 175))
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            textView.setTextColor(Color.DKGRAY)
        }

// Listener eingfügen
        textView.setOnClickListener{
            if(onItemClickListener != null) {
                onItemClickListener!!.onItemClick(shoppingMemos.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return shoppingMemos.size
    }

// Listener über Änderung informieren
    fun setShoppingMemos(memos: List<ShoppingMemo>) {
        shoppingMemos = memos
        notifyDataSetChanged()
    }

    class ShoppingMemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}