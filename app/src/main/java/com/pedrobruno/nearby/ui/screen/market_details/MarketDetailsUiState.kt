package com.pedrobruno.nearby.ui.screen.market_details

import com.pedrobruno.nearby.data.model.Rule

data class MarketDetailsUiState(
    val rules: List<Rule>? = null,
    val coupon: String? = null
)
