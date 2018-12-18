package org.gnvo.climb.tracking.climbtracker.data.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import org.gnvo.climb.tracking.climbtracker.data.room.pojo.Attempt
import org.gnvo.climb.tracking.climbtracker.data.room.pojo.AttemptWithGrades

@Dao
interface AttemptDao {
    @Query(
        "SELECT * " +
                "FROM attempt " +
                "LEFT JOIN location on location.location_id = attempt.location " +
                "ORDER BY attempt.instant DESC"
    )
    fun getAllWithGrades(): LiveData<List<AttemptWithGrades>>

    @Query(
        "SELECT attempt.*, location.* " +
                "FROM attempt " +
                "LEFT JOIN location on location.location_id = attempt.location " +
                "WHERE attempt.id = :attemptId "
    )
    fun getByIdWithGrades(attemptId: Long): LiveData<AttemptWithGrades>

    @Query(
        "SELECT attempt.*, location.* " +
                "FROM attempt " +
                "LEFT JOIN location on location.location_id = attempt.location " +
                "WHERE attempt.id = (SELECT max(id) FROM attempt)"
    )
    fun getLastWithGrades(): LiveData<AttemptWithGrades>

    @Insert
    fun insert(attempt: Attempt?)

    @Update
    fun update(attempt: Attempt)

    @Query("DELETE FROM attempt WHERE id = :attemptId")
    fun delete(attemptId: Long)
}
