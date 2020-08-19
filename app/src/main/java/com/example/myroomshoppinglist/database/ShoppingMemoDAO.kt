package com.example.myroomshoppinglist.database


import androidx.lifecycle.LiveData
import androidx.room.*

/*
    DAO: Interface mit annotation
    @Query("SELECT * FROM shopping_list") -> in Klammer SQL Statement
 */

@Dao
interface ShoppingMemoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: ShoppingMemo)

//    fun update(memo: ShoppingMemo)
//    update wird durch das Konflikthandling von insert ersetzt

    @Delete
    fun delete(memo: ShoppingMemo)

    @Query("SELECT * FROM shopping_list")
    fun getAllShoppingMemos(): LiveData<List<ShoppingMemo>>

    // nur zur erklärung
//    @Query("SELECT * FROM shopping_list WHERE product = :product")
//    fun getShoppingMemoByProduct(product: String) : ShoppingMemo

}