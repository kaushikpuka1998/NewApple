package com.kgstrivers.myapplication.Models

data class AddsData(
    val facilities: List<Facility>,
    val exclusions: List<List<Exclusion>>
)
