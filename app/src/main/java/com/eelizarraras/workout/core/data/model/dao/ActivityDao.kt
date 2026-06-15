package com.eelizarraras.workout.core.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eelizarraras.workout.core.data.model.entity.ActivityEntity

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activity WHERE uid = :uid")
    fun getActivity(vararg uid: Long): Array<ActivityEntity>

    @Query("SELECT COUNT(DISTINCT workoutId) FROM activity WHERE routineId = :uid")
    fun countWorkouts(uid: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg activity: ActivityEntity): LongArray

    @Delete
    fun delete(activity: ActivityEntity)
}