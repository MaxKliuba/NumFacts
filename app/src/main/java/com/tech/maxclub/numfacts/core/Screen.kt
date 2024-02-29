package com.tech.maxclub.numfacts.core

sealed class Screen(val route: String) {
    data object NumFacts : Screen("num_facts_list")

    data object NumFactDetail : Screen("num_fact_detail") {
        const val ARG_NUM_FACT_ID = "numFactId"
        const val DEFAULT_NUM_FACT_ID = 0

        val routeWithArgs: String
            get() = "$route/{$ARG_NUM_FACT_ID}"
    }
}