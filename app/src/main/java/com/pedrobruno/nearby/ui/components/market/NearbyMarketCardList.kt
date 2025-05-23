package com.pedrobruno.nearby.ui.components.market

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pedrobruno.nearby.data.model.Market
import com.pedrobruno.nearby.data.model.mock.mockMarkets
import com.pedrobruno.nearby.ui.theme.Typography

@Composable
fun NearbyMarketCardList(
    modifier: Modifier = Modifier,
    markets: List<Market>,
    onMarketClick: (Market) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(text = "Explore locais perto de você", style = Typography.bodyLarge)
        }
        items(items = markets, key = { it.id }) { market ->
            NearbyMarketCard(market = market) {
                onMarketClick(market)
            }
        }
    }
}

@Preview
@Composable
private fun NearbyMarketCardListPreview() {
    NearbyMarketCardList(
        modifier = Modifier.fillMaxWidth(),
        markets = mockMarkets,
        onMarketClick = {}
    )
}