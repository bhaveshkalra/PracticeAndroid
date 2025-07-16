package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * What is a ViewModel?
 * User Input ⟶ View (Activity/Fragment)
 *             ⬇️ observe()
 *         ViewModel (business logic)
 *             ⬇️ LiveData
 *         Model (data layer - Room/API)
 * Bridges View & Model. Holds UI data, survives config changes.
 * Lifecycle-aware component.
 * Stores and manages UI-related data in a lifecycle-conscious way.
 * Prevents data loss during configuration changes.
 * All data sources (Room, Retrofit) connect to ViewModel, not directly to View*/

class NameViewModel : ViewModel() {
    /**
    What is LiveData?
    Observable data holder.
    View (Activity/Fragment) observes LiveData → UI auto-updates when data changes.**/
    private val _nameList = MutableLiveData<MutableList<Name>>(mutableListOf())
    val nameList: LiveData<MutableList<Name>> = _nameList

    private val _filteredList = MutableLiveData<List<Name>>()
    val filteredList: LiveData<List<Name>> = _filteredList

    fun addName(name: Name) {
        val current = _nameList.value ?: mutableListOf()
        current.add(name)
        _nameList.value = current
        _filteredList.value = current // Keep filtered list updated
    }

    fun deleteName(position: Int) {
        val current = _nameList.value ?: return
        current.removeAt(position)
        _nameList.value = current
        _filteredList.value = current
    }

    fun filter(query: String) {
        val fullList = _nameList.value ?: return
        _filteredList.value = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter {
                it.fullName.contains(query, ignoreCase = true)
            }
        }
    }
}
