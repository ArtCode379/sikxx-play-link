package sikxx.toys.sikxxplaylink.ui.composable.screen.productdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import sikxx.toys.sikxxplaylink.R
import sikxx.toys.sikxxplaylink.data.model.Product
import sikxx.toys.sikxxplaylink.ui.composable.shared.TNQRSContentWrapper
import sikxx.toys.sikxxplaylink.ui.composable.shared.TNQRSEmptyView
import sikxx.toys.sikxxplaylink.ui.state.DataUiState
import sikxx.toys.sikxxplaylink.ui.viewmodel.ProductDetailsViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = koinViewModel()
) {
    val productState by viewModel.productDetailsState.collectAsState()
    LaunchedEffect(productId) {
        viewModel.observeProductDetails(productId)
    }
    TNQRSContentWrapper(
        dataState = productState,
        dataPopulated = {
            ProductDetails(
                product = (productState as DataUiState.Populated).data,
                modifier = modifier,
                onAddToCart = viewModel::addProductToCart
            )
        },
        dataEmpty = {
            TNQRSEmptyView(
                primaryText = stringResource(R.string.tnqrs_product_details_state_empty_primary_text),
                modifier = Modifier.fillMaxSize()
            )
        }
    )
}

@Composable
private fun ProductDetails(
    product: Product,
    modifier: Modifier,
    onAddToCart: () -> Unit
) {
    var cartAdded by remember { mutableStateOf(false) }
    LaunchedEffect(cartAdded) {
        if (cartAdded) {
            delay(2000)
            cartAdded = false
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 72.dp)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            )
            Column(modifier = Modifier.padding(20.dp)) {
                Text(product.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.tnqrs_price, product.price),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(product.category.titleRes),
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
                Spacer(Modifier.height(20.dp))
                Text(stringResource(R.string.tnqrs_about_this_toy), style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        onAddToCart()
                        cartAdded = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(stringResource(R.string.tnqrs_button_add_to_cart_label))
                }
            }
        }
        AnimatedVisibility(
            visible = cartAdded,
            enter = slideInVertically { it },
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(R.string.tnqrs_added_to_cart),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp)
            )
        }
    }
}
