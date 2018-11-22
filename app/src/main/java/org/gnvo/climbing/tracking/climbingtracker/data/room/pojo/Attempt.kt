package org.gnvo.climbing.tracking.climbingtracker.data.room.pojo

import android.arch.persistence.room.*
import java.time.LocalDateTime

@Entity(
    tableName = "attempt",
    foreignKeys = [
        ForeignKey(entity = RouteGrade::class, parentColumns = ["route_grade_id"], childColumns = ["route_grade"])]
)
data class Attempt(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "route_grade") var routeGrade: Long,

    var datetime: LocalDateTime,
    @ColumnInfo(name = "route_name") var routeName: String? = null,
    var comment: String? = null,
    var rating: Int? = null,

    var outcome: String,
    @ColumnInfo(name = "route_type") var routeType: String? = null,
    @ColumnInfo(name = "climbing_style") var climbingStyle: String,
    @ColumnInfo(name = "route_style") var routeStyle: List<String>? = null,
    var length: Int? = null,

    @Embedded var location: Location? = null
) {
    override fun equals(other: Any?): Boolean {
        return other is Attempt &&
                location?.equals(other.location) ?: false &&
                routeGrade == other.routeGrade &&
                datetime == other.datetime &&
                routeName == other.routeName &&
                comment == other.comment &&
                rating == other.rating &&
                outcome == other.outcome &&
                routeType == other.routeType &&
                climbingStyle == other.climbingStyle &&
                routeStyle == other.routeStyle &&
                length == other.length
    }
}
