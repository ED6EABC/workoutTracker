package com.eelizarraras.workout.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eelizarraras.workout.core.data.local.entity.ActivityEntity

@Dao
interface ActivityDao {
    @Query("SELECT * FROM Activity")
    fun getAllActivities(): List<ActivityEntity>

    @Insert
    fun insert(activity: ActivityEntity)

    @Delete
    fun delete(activity: ActivityEntity)
}