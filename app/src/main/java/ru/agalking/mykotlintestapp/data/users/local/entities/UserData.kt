package ru.agalking.mykotlintestapp.data.users.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_table", // User Entity represents a table within the database.
        indices = [ Index("email", unique = true),
                    Index(value = ["first_name", "last_name"])])
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String?,
    @ColumnInfo(name = "last_name")
    val lastName: String?,
    val email: String,
    @ColumnInfo(name = "balance")
    val balance: Double,
    @ColumnInfo(name = "photo_path")
    val photoPath: String,
)
