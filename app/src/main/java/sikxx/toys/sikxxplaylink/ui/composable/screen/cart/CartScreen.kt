package sikxx.toys.sikxxplaylink.ui.composable.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.RemoveShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import sikxx.toys.sikxxplaylink.R
import sikxx.toys.sikxxplaylink.ui.composable.shared.TNQRSContentWrapper
import sikxx.toys.sikxxplaylink.ui.state.CartItemUiState
import sikxx.toys.sikxxplaylink.ui.state.DataUiState
import sikxx.toys.sikxxplaylink.ui.viewmodel.CartViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onNavigateToCheckoutScreen: () -> Unit
) {
    val state by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val total by viewModel.totalPrice.collectAsStateWithLifecycle()
    TNQRSContentWrapper(
        dataState = state,
        dataPopulated = {
            CartContent(
                items = (state as DataUiState.Populated).data,
                total = total,
                modifier = modifier,
                onPlus = viewModel::incrementProductInCart,
                onMinus = { item ->
                    if (item.quantity == 1) {
                        viewModel.deleteFromCart(item.productId)
                    } else {
                        viewModel.decrementItemInCart(item.productId)
                    }
                },
                onDelete = viewModel::deleteFromCart,
                onCheckout = onNavigateToCheckoutScreen
            )
        },
        dataEmpty = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Rounded.RemoveShoppingCart, contentDescription = null, modifier = Modifier.size(72.dp), tint = MaterialTheme.colorScheme.primary)
                Text(stringResource(R.string.tnqrs_cart_state_empty_primary_text), style = MaterialTheme.typography.titleLarge)
                Text(stringResource(R.string.tnqrs_start_shopping), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    )
}

@Composable
private fun CartContent(
    items: List<CartItemUiState>,
    total: Double,
    modifier: Modifier,
    onPlus: (Int) -> Unit,
    onMinus: (CartItemUiState) -> Unit,
    onDelete: (Int) -> Unit,
    onCheckout: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(stringResource(R.string.tnqrs_your_cart), style = MaterialTheme.typography.headlineMedium)
        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items, key = { it.productId }) { item ->
                Card(shape = RoundedCornerShape(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = item.productImageUrl,
                            contentDescription = item.productTitle,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(64.dp)
                        )
                        Column(modifier = Modifier.weight(1f).padding(horizontal = 10.dp)) {
                            Text(item.productTitle, style = MaterialTheme.typography.titleMedium)
                            Text(stringResource(R.string.tnqrs_price, item.productPrice), color = MaterialTheme.colorScheme.primary)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextButton(onClick = { onMinus(item) }) { Text("−") }
                                Text(item.quantity.toString(), fontWeight = FontWeight.Bold)
                                TextButton(onClick = { onPlus(item.productId) }) { Text("+") }
                            }
                        }
                        IconButton(onClick = { onDelete(item.productId) }) {
                            Icon(Icons.Rounded.DeleteOutline, contentDescription = stringResource(R.string.tnqrs_delete_item_icon_description))
                        }
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.tnqrs_total), style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.tnqrs_price, total), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        }
        Button(onClick = onCheckout, modifier = Modifier.fillMaxWidth().height(54.dp), shape = RoundedCornerShape(16.dp)) {
            Text(stringResource(R.string.tnqrs_proceed_checkout))
        }
    }
}
