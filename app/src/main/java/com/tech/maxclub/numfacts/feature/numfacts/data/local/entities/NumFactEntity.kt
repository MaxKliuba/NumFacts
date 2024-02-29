package com.tech.maxclub.numfacts.feature.numfacts.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumType

@Entity(tableName = "num_facts")
data class NumFactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: NumType,
    val number: String,
    val fact: String,
    val timestamp: Long,
    val deleted: Boolean = false,
)
