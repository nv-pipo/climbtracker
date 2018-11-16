package tracking.climbing.gnvo.org.climbingtracker.data.room.pojo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(tableName = "climbing_entry")
data class ClimbEntry(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String? = null,
    val coordinates: String? = null,
    val site: String? = null,
    val sector: String? = null,
    val datetime: Date,
    var pitches: List<Pitch>,//Todo: Don't use JSON to store pitches
    @ColumnInfo(name = "route_type") val routeType: String,
    var rating: Int? = null,
    val comment: String? = null
)
{
    companion object {
        fun initialData(): List<ClimbEntry> {
            return listOf(
                ClimbEntry(
                    routeType = "Sport",
                    datetime = Date(0),
                    pitches = listOf(Pitch(routeGradeId = 2, pitchNumber = 1))
                )
            )
        }
    }
}
