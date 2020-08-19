package com.example.myroomshoppinglist

import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroomshoppinglist.database.ShoppingMemo
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_edit_shopping_memo.view.*

class MainActivity : AppCompatActivity() {

    lateinit var shoppingListViewModel: ShoppingMemoViewModel
    private val adapter: ShoppingMemoListAdapter = ShoppingMemoListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv_shopping_memos.layoutManager = LinearLayoutManager(this)
        lv_shopping_memos.adapter = adapter

        shoppingListViewModel = ShoppingMemoViewModel(application)
        // Dem Adapter Mittielen, das die Werte g
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

// Region Touchhelper
        var itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentMemo = shoppingListViewModel.getAllShoppingMemos().value!!.get(viewHolder.adapterPosition)
                when(direction) {
                    ItemTouchHelper.RIGHT -> {
                        shoppingListViewModel.delete(currentMemo)
                    }
                    ItemTouchHelper.LEFT -> {
                        val builder =AlertDialog.Builder(this@MainActivity)
                        val dialogsView  = layoutInflater.inflate(R.layout.dialog_edit_shopping_memo, null)
                        dialogsView.et_change_quantity.setText(currentMemo.quantity.toString())
                        dialogsView.et_change_product.setText(currentMemo.product)
                        builder
                            .setView(dialogsView)
                            .setTitle("Eintrag ändern")
                            .setPositiveButton("Ändern"){ dialog: DialogInterface, which: Int ->
                                currentMemo.quantity = dialogsView.et_change_quantity.text.toString().toInt()
                                currentMemo.product = dialogsView.et_change_product.text.toString()
                                shoppingListViewModel.insert(currentMemo)
                                dialog.dismiss()
                            }
                            .setNegativeButton("Abbrechen") { dialog: DialogInterface, which: Int ->
                                adapter.notifyDataSetChanged()
                                dialog.cancel()
                            }
                            .create().show()
                    }
                }
            }
// Icons für doe aktionen nach links und nach rechts swipen einfügen
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                RecyclerViewSwipeDecorator.Builder(c,recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(Color.LTGRAY)
                    .addSwipeLeftActionIcon(R.drawable.ic_edit_black_24dp)
                    .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })
        itemTouchHelper.attachToRecyclerView(lv_shopping_memos)
// endregion

// Region Add-Button
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
// endregion

    }

}