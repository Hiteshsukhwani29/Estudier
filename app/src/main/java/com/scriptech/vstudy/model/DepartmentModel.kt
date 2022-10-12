package com.scriptech.vstudy.model

import androidx.room.PrimaryKey

data class DepartmentModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var sub_name: String? = null,
    var sub_link: String? = null,
)
