package com.example.myroomshoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myroomshoppinglist.database.ShoppingMemo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var shoppingListViewModel: ShoppingMemoViewModel
    private val adapter: ShoppingMemoListAdapter = ShoppingMemoListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lv_shopping_memos.layoutManager = LinearLayoutManager(this)
        lv_shopping_memos.adapter = adapter

        shoppingListViewModel = ShoppingMemoViewModel(application)
        shoppingListViewModel.getAllShoppingMemos().observe(this, Observer {
            adapter.setShoppingMemos(it)
        })
        // Listener für Listview aufbauen und onclick Implementieren
        // Das Beobachten wird vom System geliefert
        adapter.setOnItemClickListener(object : ShoppingMemoListAdapter.OnItemClickListener {

            override fun onItemClick(memo: ShoppingMemo) {
                memo.isChecked = !memo.isChecked
                shoppingListViewModel.insert(memo)
            }

        })

        btn_add_product.setOnClickListener {
            if(TextUtils.isEmpty(et_quantity.text)) {
                et_quantity.error = "feld darf nicht leer sein"
                et_quantity.requestFocus()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(et_product.text)) {
                et_product.error = "feld darf nicht leer sein"
                et_product.requestFocus()
                return@setOnClickListener
            }
            shoppingListViewModel.insert(ShoppingMemo(
                et_quantity.text.toString().toInt(),
                et_product.text.toString()))
            et_quantity.text.clear()
            et_product.text.clear()
            et_quantity.requestFocus()
        }

    }

}