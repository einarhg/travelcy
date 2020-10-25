package com.travelcy.travelcy.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.travelcy.travelcy.model.Currency

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency where id = :currencyCode")
    fun getCurrency(currencyCode: String): LiveData<Currency>

    @Query("SELECT * FROM currency where id = :currencyCode limit 1")
    fun getRawCurrency(currencyCode: String): Currency?

    @Query("SELECT COUNT(*) FROM currency")
    fun hasCurrencies(): Boolean

    @Query("SELECT * FROM currency")
    fun loadCurrencies(): LiveData<List<Currency>>

    @Insert
    fun insertCurrency(currency: Currency)

    @Update
    fun updateCurrency(currency: Currency)

    @Update
    fun updateCurrencies(currencies: List<Currency>)
}