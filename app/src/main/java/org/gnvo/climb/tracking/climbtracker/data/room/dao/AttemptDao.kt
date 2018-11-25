package org.gnvo.climb.tracking.climbtracker.data.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import org.gnvo.climb.tracking.climbtracker.data.room.pojo.Attempt
import org.gnvo.climb.tracking.climbtracker.data.room.pojo.AttemptWithDetails

@Dao
interface AttemptDao {
    @Query(
        "SELECT attempt.*, climb_style.*, outcome.*, route_grade.*, route_type.* " +
                "FROM attempt " +
                "INNER JOIN climb_style on climb_style.climb_style_id = attempt.climb_style " +
                "INNER JOIN outcome on outcome.outcome_id = attempt.outcome " +
                "INNER JOIN route_grade on route_grade.route_grade_id = attempt.route_grade " +
                "INNER JOIN route_type on route_type.route_type_id = attempt.route_type " +
                "ORDER BY datetime ASC"
    )
    fun getAllWithDetails(): LiveData<List<AttemptWithDetails>>

    @Query("SELECT attempt.*, climb_style.*, outcome.*, route_grade.*, route_type.* " +
            "FROM attempt " +
            "INNER JOIN climb_style on climb_style.climb_style_id = attempt.climb_style " +
            "INNER JOIN outcome on outcome.outcome_id = attempt.outcome " +
            "INNER JOIN route_grade on route_grade.route_grade_id = attempt.route_grade " +
            "INNER JOIN route_type on route_type.route_type_id = attempt.route_type " +
            "WHERE attempt.id = :attemptId " +
            "ORDER BY datetime ASC"
    )
    fun getByIdWithDetails(attemptId:Long): LiveData<AttemptWithDetails>

    @Query("DELETE FROM attempt")
    fun deleteAll()

    @Insert
    fun insert(attempt: Attempt?)

    @Update
    fun update(attempt: Attempt)

    @Delete
    fun delete(attempt: Attempt)
}