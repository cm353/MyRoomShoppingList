package com.example.myroomshoppinglist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*
    Die datenklasse wird als data class ausgeführt und mit der annotation @Entity
    aus androidx.room.Entity versehen
    @Ignore -> Feld ignorieren in DB
    @ColumnInfo(name ="is_checked") -> Namen der Spalte im Tabelle anders
    @PrimaryKey(autoGenerate = true) -> Primärschlüssel mit automatischem hochzählen
 */

@Entity(tableName = "shopping_list")
data class ShoppingMemo(var quantity: Int, var product: String, @ColumnInfo(name ="is_checked") var isChecked: Boolean=false) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return "$quantity x $product"
    }
}