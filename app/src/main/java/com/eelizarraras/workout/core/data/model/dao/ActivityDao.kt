package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.eelizarraras.workout.core.data.model.entity.ActivityEntity

@Dao
interface ActivityDao {
    @Query("SELECT * FROM Activity WHERE uid = :uid")
    fun getActivity(uid: Int): ActivityEntity?

    @Insert
    fun insert(activity: ActivityEntity): Long

    @Delete
    fun delete(activity: ActivityEntity)
}