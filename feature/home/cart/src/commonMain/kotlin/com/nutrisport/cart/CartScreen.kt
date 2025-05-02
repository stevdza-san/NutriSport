package com.nutrisport.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.nutrisport.shared.util.RequestState
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nutrisport.cart.component.CartItemCard
import com.nutrisport.shared.Resources
import com.nutrisport.shared.component.InfoCard
import com.nutrisport.shared.component.LoadingCard
import com.nutrisport.shared.util.DisplayResult

@Composable
fun CartScreen() {
    val viewModel = koinViewModel<CartViewModel>()
    val cartItemsWithProducts by viewModel.cartItemsWithProducts.collectAsState(RequestState.Loading)

    cartItemsWithProducts.DisplayResult(
        onLoading = { LoadingCard(modifier = Modifier.fillMaxSize()) },
        onSuccess = { data ->
            if (data.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = data,
                        key = { data.hashCode().toString() }
                    ) { pair ->
                        CartItemCard(
                            cartItem = pair.first,
                            product = pair.second,
                            onMinusClick = {},
                            onPlusClick = {},
                            onDeleteClick = {}
                        )
                    }
                }
            } else {
                InfoCard(
                    image = Resources.Image.ShoppingCart,
                    title = "Empty Cart",
                    subtitle = "Check some of our products."
                )
            }
        },
        onError = { message ->
            InfoCard(
                image = Resources.Image.Cat,
                title = "Oops!",
                subtitle = message
            )
        }
    )
}