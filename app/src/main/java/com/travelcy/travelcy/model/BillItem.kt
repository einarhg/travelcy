package com.travelcy.travelcy.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "bill_items", foreignKeys = [ForeignKey(entity = Bill::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("billId"),
    onDelete = ForeignKey.CASCADE)],
    indices = []
)
class BillItem(
    val description: String,
    val amount: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var billId = 1
}