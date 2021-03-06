package com.travelcy.travelcy.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.travelcy.travelcy.model.*


@Dao
interface BillDao {
    @Insert(onConflict = REPLACE)
    fun createBill(bill: Bill)

    @Query("SELECT COUNT(*) FROM bills")
    fun hasBill(): Boolean

    @Query("SELECT * FROM bills where id = 1")
    fun getBill(): LiveData<Bill>

    @Query("UPDATE bills set tipAmount = :amount where id = 1")
    fun setTipAmount(amount: Double?)

    @Query("UPDATE bills set tipPercentage = :percentage where id = 1")
    fun setTipPercentage(percentage: Double?)

    @Query("UPDATE bills set taxPercentage = :percentage where id = 1")
    fun setTaxPercentage(percentage: Double)

    @Insert(onConflict = REPLACE)
    fun addBillItem(billItem: BillItem): Long

    @Update
    fun updateBillItem(billItem: BillItem)

    fun addBillItemToBill(billItem: BillItem): Long {
        billItem.billId = 1
        return addBillItem(billItem)
    }

    @Query("DELETE FROM person_bill_item where billItemId = :billItemId and personId = :personId")
    fun detatchPersonFromBillItem(billItemId: Int, personId: Int)

    @Query("INSERT OR IGNORE INTO person_bill_item (billItemId, personId) VALUES (:billItemId, :personId)")
    fun attatchPersonToBillItem(billItemId: Int, personId: Int)

    @Query("SELECT COUNT(*) FROM persons")
    fun hasPersons(): Boolean

    @Insert(onConflict = REPLACE)
    fun addPerson(person: Person): Long

    fun addPersonToBillItem(billItem: BillItem, person: Person) {
        if (billItem.id != null && person.id != null) {
            attatchPersonToBillItem(billItem.id as Int, person.id as Int)
        }
    }

    fun removePersonFromBillItem(billItem: BillItem, person: Person) {
        if (person.id != null && billItem.id != null) {
            detatchPersonFromBillItem(billItem.id as Int, person.id as Int)
        }
    }

    @Query("select * from bill_items where billId = 1")
    fun getBillItemsWithPersons(): LiveData<List<BillItemWithPersons>>

    @Query("select * from persons p left join person_bill_item pi on pi.personId = p.id where pi.billItemId = :billItemId")
    fun getPersonsForBillItem(billItemId: Int): LiveData<List<Person>>

    @Update
    fun updatePerson(person: Person)

    @Query("select * from persons where isDefault = 1")
    fun getDefaultPerson(): LiveData<Person>

    @Query("select * from persons")
    fun getAllPersons(): LiveData<List<Person>>

    @Query("select * from bill_items where id = :billItemId")
    fun getBillItem(billItemId: Int): LiveData<BillItem>

    @Delete
    fun deleteBillItem(billItem: BillItem)
}