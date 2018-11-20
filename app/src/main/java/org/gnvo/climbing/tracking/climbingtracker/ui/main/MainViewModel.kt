package org.gnvo.climbing.tracking.climbingtracker.ui.main

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.AndroidViewModel
import org.gnvo.climbing.tracking.climbingtracker.data.room.pojo.*
import org.gnvo.climbing.tracking.climbingtracker.data.room.repository.ClimbEntryRepository
import org.gnvo.climbing.tracking.climbingtracker.data.room.repository.PitchRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryClimbEntry: ClimbEntryRepository = ClimbEntryRepository(application)
    private val repositoryPitch: PitchRepository = PitchRepository(application)

    private val climbingEntriesSummary: LiveData<List<ClimbEntrySummary>> = repositoryClimbEntry.getAllSummary()

    fun deleteClimbEntry(climbEntry: ClimbEntry) {
        repositoryClimbEntry.delete(climbEntry)
    }

    fun deleteAllClimbingEntries() {
        repositoryClimbEntry.deleteAllClimbingEntries()
    }

    fun getAllClimbingEntriesSummary(): LiveData<List<ClimbEntrySummary>> {
        return climbingEntriesSummary
    }

    fun deleteClimbEntryById(climbEntryId: Long?) {
        repositoryPitch.deleteClimbEntryById(climbEntryId)
        repositoryClimbEntry.deleteClimbEntryById(climbEntryId)
    }
}
