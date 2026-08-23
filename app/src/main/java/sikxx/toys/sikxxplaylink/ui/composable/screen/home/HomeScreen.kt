package sikxx.toys.sikxxplaylink.ui.composable.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import sikxx.toys.sikxxplaylink.R
import sikxx.toys.sikxxplaylink.data.model.Product
import sikxx.toys.sikxxplaylink.data.model.ProductCategory
import sikxx.toys.sikxxplaylink.ui.composable.shared.TNQRSContentWrapper
import sikxx.toys.sikxxplaylink.ui.composable.shared.TNQRSEmptyView
import sikxx.toys.sikxxplaylink.ui.state.DataUiState
import sikxx.toys.sikxxplaylink.ui.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit
) {
    val productsState by viewModel.productsState.collectAsState()
    TNQRSContentWrapper(
        dataState = productsState,
        dataPopulated = {
            HomeProducts(
                products = (productsState as DataUiState.Populated).data,
                modifier = modifier,
                onProductClick = onNavigateToProductDetails
            )
        },
        dataEmpty = {
            TNQRSEmptyView(
                primaryText = stringResource(R.string.tnqrs_products_state_empty_primary_text),
                modifier = Modifier.fillMaxSize()
            )
        }
    )
}

@Composable
private fun HomeProducts(
    products: List<Product>,
    modifier: Modifier,
    onProductClick: (Int) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }
    val featured = products.take(4)
    val pagerState = rememberPagerState(pageCount = { featured.size })
    val filtered = selectedCategory?.let { category -> products.filter { it.category == category } } ?: products

    LaunchedEffect(pagerState.currentPage, featured.size) {
        delay(4000)
        if (featured.isNotEmpty()) {
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % featured.size)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.tnqrs_app_name), style = MaterialTheme.typography.titleLarge)
                Text(
                    text = stringResource(R.string.tnqrs_home_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = { selectedCategory = null }) {
                Icon(Icons.Rounded.Search, contentDescription = stringResource(R.string.tnqrs_search))
            }
        }
        HorizontalPager(
            state = pagerState,
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
            pageSpacing = 12.dp
        ) { page ->
            val product = featured[page]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clickable { onProductClick(product.id) },
                shape = RoundedCornerShape(20.dp)
            ) {
                Box {
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = product.title,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.9f))
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            featured.indices.forEach { index ->
                Spacer(
                    Modifier
                        .padding(3.dp)
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.outline
                            }
                        )
                )
            }
        }
        LazyRow(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                AssistChip(onClick = { selectedCategory = null }, label = { Text(stringResource(R.string.tnqrs_category_all)) })
            }
            items(ProductCategory.entries.size) { index ->
                val category = ProductCategory.entries[index]
                AssistChip(onClick = { selectedCategory = category }, label = { Text(stringResource(category.titleRes)) })
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filtered, key = { it.id }) { product ->
                ProductCard(product = product, onClick = { onProductClick(product.id) })
            }
        }
    }
}

@Composable
private fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Column(modifier = Modifier.padding(12.dp)) {
            Text(product.title, style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(6.dp))
            Text(stringResource(R.string.tnqrs_price, product.price), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Text(stringResource(product.category.titleRes), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
