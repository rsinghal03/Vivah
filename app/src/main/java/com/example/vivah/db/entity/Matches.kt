package com.example.vivah.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vivah.db.model.MatchesResponse
import com.google.gson.annotations.SerializedName

@Entity
data class Matches(
    @PrimaryKey
    val id: String,
    @Embedded
    val name: MatchesResponse.Result.Name,
    @SerializedName("isAccepted")
    val statusIsAccepted: Boolean? = null, //
    @SerializedName("largePhoto")
    val photo: String,
    val age: Int,
    val city: String,
    val state: String
)

