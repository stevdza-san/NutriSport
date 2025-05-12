package com.nutrisport.payment_completed

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nutrisport.data.domain.ProductRepository
import com.nutrisport.shared.util.RequestState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.nutrisport.data.domain.CustomerRepository
import com.nutrisport.data.domain.OrderRepository
import com.nutrisport.shared.domain.CartItem
import com.nutrisport.shared.domain.Order
import com.nutrisport.shared.domain.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val customerRepository: CustomerRepository,
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    var screenState: RequestState<Unit> by mutableStateOf(RequestState.Loading)

    private val customer = customerRepository.readCustomerFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Loading
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val totalAmount = customer.flatMapLatest { customerState ->
        when {
            customerState.isSuccess() -> {
                val cartItems = customerState.getSuccessData().cart
                val productIds = cartItems.map { it.productId }

                if (productIds.isEmpty()) {
                    flowOf(RequestState.Success(0.0))
                } else {
                    productRepository.readProductsByIdsFlow(productIds)
                        .map { products ->
                            if (products.isSuccess()) {
                                RequestState.Success(
                                    calculateTotalPrice(
                                        cartItems = cartItems,
                                        products = products.getSuccessData()
                                    )
                                )
                            } else {
                                RequestState.Error(products.getErrorMessage())
                            }
                        }
                }
            }
            customerState.isError() -> flowOf(RequestState.Error(customerState.getErrorMessage()))
            else -> flowOf(RequestState.Loading)
        }
    }

    init {
        viewModelScope.launch {
            totalAmount.collectLatest { amount ->
                if (amount.isSuccess()) {
                    val isSuccess = savedStateHandle.get<Boolean>("isSuccess")
                    val error = savedStateHandle.get<String>("error")
                    val token = savedStateHandle.get<String>("token")

                    if (isSuccess != null) {
                        screenState = RequestState.Success(Unit)
                        if (token != null) {
                            createTheOrder(
                                totalAmount = amount.getSuccessData(),
                                token = token,
                                onError = { message ->
                                    screenState = RequestState.Error(message)
                                }
                            )
                        }
                    } else if (error != null) {
                        screenState = RequestState.Error(error)
                    } else {
                        screenState =
                            RequestState.Error("Unknown error. Contact us at: example@gmail.com")
                    }
                } else if (amount.isError()) {
                    screenState = RequestState.Error(amount.getErrorMessage())
                }
            }
        }
    }

    private fun createTheOrder(
        totalAmount: Double,
        token: String,
        onError: (String) -> Unit,
    ) {
        if (customer.value.isSuccess()) {
            val customerId = customer.value.getSuccessData().id
            viewModelScope.launch(Dispatchers.IO) {
                orderRepository.createTheOrder(
                    order = Order(
                        customerId = customerId,
                        items = customer.value.getSuccessData().cart,
                        totalAmount = totalAmount,
                        token = token
                    ),
                    onSuccess = { println("ORDER SUCCESSFULLY CREATED!") },
                    onError = { message -> onError(message) }
                )
            }
        } else if (customer.value.isError()) onError(customer.value.getErrorMessage())
    }

    fun calculateTotalPrice(cartItems: List<CartItem>, products: List<Product>): Double {
        return cartItems.sumOf { cartItem ->
            val product = products.find { it.id == cartItem.productId }
            product?.price?.times(cartItem.quantity) ?: 0.0
        }
    }
}