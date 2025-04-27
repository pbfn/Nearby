package com.pedrobruno.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pedrobruno.nearby.data.model.Market
import com.pedrobruno.nearby.ui.screen.home.HomeScreen
import com.pedrobruno.nearby.ui.screen.home.HomeViewModel
import com.pedrobruno.nearby.ui.screen.market_details.MarketDetailsScreen
import com.pedrobruno.nearby.ui.screen.market_details.MarketDetailsViewModel
import com.pedrobruno.nearby.ui.screen.splash.SplashScreen
import com.pedrobruno.nearby.ui.screen.welcome.WelcomeScreen
import com.pedrobruno.nearby.ui.route.Home
import com.pedrobruno.nearby.ui.route.QRCodeScanner
import com.pedrobruno.nearby.ui.route.Splash
import com.pedrobruno.nearby.ui.route.Welcome
import com.pedrobruno.nearby.ui.screen.market_details.MarketDetailsUiEvent
import com.pedrobruno.nearby.ui.screen.qrcode_scanner.QRCodeScannerScreen
import com.pedrobruno.nearby.ui.theme.NearbyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearbyTheme {
                val navController = rememberNavController()

                val homeViewModel by viewModels<HomeViewModel>()
                val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

                val marketDetailsViewModel by viewModels<MarketDetailsViewModel>()
                val marketDetailsUiState by marketDetailsViewModel.uiState.collectAsStateWithLifecycle()

                NavHost(
                    navController = navController,
                    startDestination = Splash
                ) {
                    composable<Splash> {
                        SplashScreen(modifier = Modifier.fillMaxSize(),
                            onNavigateToWelcome = {
                                navController.navigate(Welcome)
                            }
                        )
                    }

                    composable<Welcome> {
                        WelcomeScreen(onNavigateToHome = {
                            navController.navigate(Home)
                        })
                    }

                    composable<Home> {
                        HomeScreen(
                            onNavigateToMarketDetails = { selectedMarket ->
                                navController.navigate(selectedMarket)
                            },
                            uiState = homeUiState,
                            onEvent = homeViewModel::onEvent
                        )
                    }

                    composable<Market> {
                        val selectedMarket = it.toRoute<Market>()
                        MarketDetailsScreen(market = selectedMarket,
                            uiState = marketDetailsUiState,
                            onEvent = marketDetailsViewModel::onEvent,
                            onNavigateToQRCodeScanner = {
                                navController.navigate(QRCodeScanner)
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            })
                    }

                    composable<QRCodeScanner> {
                        QRCodeScannerScreen(
                            onCompletedScan = { qrCodeContent ->
                                if (qrCodeContent.isNotEmpty())
                                    marketDetailsViewModel.onEvent(
                                        MarketDetailsUiEvent.OnFetchCoupon(
                                            qrCodeContent = qrCodeContent
                                        )
                                    )
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NearbyTheme {

    }
}