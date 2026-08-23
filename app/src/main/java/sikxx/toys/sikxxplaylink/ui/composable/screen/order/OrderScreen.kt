package sikxx.toys.sikxxplaylink.ui.composable.screen.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import sikxx.toys.sikxxplaylink.R
import sikxx.toys.sikxxplaylink.data.entity.OrderEntity
import sikxx.toys.sikxxplaylink.ui.composable.shared.TNQRSContentWrapper
import sikxx.toys.sikxxplaylink.ui.composable.shared.TNQRSEmptyView
import sikxx.toys.sikxxplaylink.ui.state.DataUiState
import sikxx.toys.sikxxplaylink.ui.theme.Success
import sikxx.toys.sikxxplaylink.ui.viewmodel.OrderViewModel
import java.time.format.DateTimeFormatter

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = koinViewModel()
) {
    val state by viewModel.ordersState.collectAsState()
    TNQRSContentWrapper(
        dataState = state,
        dataPopulated = {
            OrderList(
                orders = (state as DataUiState.Populated).data.sortedByDescending { it.timestamp },
                modifier = modifier
            )
        },
        dataEmpty = {
            TNQRSEmptyView(
                primaryText = stringResource(R.string.tnqrs_orders_state_empty_primary_text),
                modifier = Modifier.fillMaxSize()
            )
        }
    )
}

@Composable
private fun OrderList(orders: List<OrderEntity>, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(stringResource(R.string.tnqrs_purchase_history), style = MaterialTheme.typography.headlineMedium)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(orders, key = { it.orderNumber }) { order ->
                Card(shape = RoundedCornerShape(18.dp)) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(stringResource(R.string.tnqrs_order_number, order.orderNumber), fontWeight = FontWeight.Bold)
                            Text(stringResource(R.string.tnqrs_reserved), color = Success, fontWeight = FontWeight.Bold)
                        }
                        Text(order.timestamp.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(order.description, style = MaterialTheme.typography.bodyMedium)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(stringResource(R.string.tnqrs_ready_24_hours), color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(stringResource(R.string.tnqrs_price, order.price), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
