package com.example.myroomshoppinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myroomshoppinglist.database.ShoppingMemo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingMemoViewModel(app: Application) : AndroidViewModel(app) {

    private var reposotory = ShoppingMemoReposotory(app)
    private var allShoppingMemos = reposotory.getAllShoppingMemos()

    fun insert(memo: ShoppingMemo) {
        CoroutineScope(Dispatchers.IO).launch {
            reposotory.insert(memo)
        }
    }

    fun delete(memo:ShoppingMemo) {
        CoroutineScope(Dispatchers.IO).launch {
            reposotory.delete(memo)
        }
    }

    fun getAllShoppingMemos() : LiveData<List<ShoppingMemo>> {
        return allShoppingMemos
    }

}