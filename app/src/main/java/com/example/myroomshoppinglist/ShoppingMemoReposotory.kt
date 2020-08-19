package com.example.myroomshoppinglist

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myroomshoppinglist.database.ShoppingMemo
import com.example.myroomshoppinglist.database.ShoppingMemoDAO
import com.example.myroomshoppinglist.database.ShoppingMemoDatabase

class ShoppingMemoReposotory(app: Application) {

    private val shoppingMemoDao: ShoppingMemoDAO
    private val allShoppingMemos: LiveData<List<ShoppingMemo>>

    init {
        val db = ShoppingMemoDatabase.Factory.getInstance(app.applicationContext)
        shoppingMemoDao = db.shoppingMemoDao()
        allShoppingMemos = shoppingMemoDao.getAllShoppingMemos()
    }

    suspend fun insert(memo: ShoppingMemo) {
        shoppingMemoDao.insert(memo)
    }

    suspend fun delete(memo: ShoppingMemo) {
        shoppingMemoDao.delete(memo)
    }

    fun getAllShoppingMemos() : LiveData<List<ShoppingMemo>> {
        return allShoppingMemos
    }

}