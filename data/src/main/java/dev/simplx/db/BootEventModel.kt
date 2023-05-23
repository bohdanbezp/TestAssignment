package dev.simplx.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BootEventModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val bootTime: Long,
)