package org.gnvo.climbing.tracking.climbingtracker.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import org.gnvo.climbing.tracking.climbingtracker.data.room.pojo.*
import org.gnvo.climbing.tracking.climbingtracker.data.room.repository.ClimbEntryRepository
import org.gnvo.climbing.tracking.climbingtracker.data.room.repository.PitchRepository

class AddEditViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryClimbEntry: ClimbEntryRepository = ClimbEntryRepository(application)
    private val repositoryPitch: PitchRepository = PitchRepository(application)

    fun insertClimbEntry(climbEntryWithPitches: ClimbEntryWithPitches) {
        repositoryClimbEntry.insert(climbEntryWithPitches)
    }

    fun updateClimbEntry(climbEntry: ClimbEntry) {
        repositoryClimbEntry.update(climbEntry)
    }

    fun deleteClimbEntryById(climbEntryId: Long?) {
        repositoryPitch.deleteClimbEntryById(climbEntryId)
        repositoryClimbEntry.deleteClimbEntryById(climbEntryId)
    }

    fun getClimbingEntryFullById(climbEntryId: Long): LiveData<ClimbEntryFull> {
        return repositoryClimbEntry.getFullById(climbEntryId)
    }
}
