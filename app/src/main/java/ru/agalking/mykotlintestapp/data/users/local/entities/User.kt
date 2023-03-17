package ru.agalking.mykotlintestapp.data.users.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_table", // User Entity represents a table within the database.
        indices = [ Index("email", unique = true),
                    Index(value = ["first_name", "last_name"])])
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String,
    var email: String,
    @ColumnInfo(name = "balance")
    var balance: Double,
    @ColumnInfo(name = "photo_url")
    var photo: String?,
) {
    constructor() : this(-1, "", "", "", 0.0, "") {
    }
}
