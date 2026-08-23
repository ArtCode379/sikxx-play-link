package sikxx.toys.sikxxplaylink.ui.composable.screen.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.FamilyRestroom
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import sikxx.toys.sikxxplaylink.R
import sikxx.toys.sikxxplaylink.ui.viewmodel.TNQRSOnboardingVM

private data class OnboardingContent(
    val title: Int,
    val description: Int,
    val icon: ImageVector
)

private val pages = listOf(
    OnboardingContent(R.string.tnqrs_page_1_title, R.string.tnqrs_page_1_description, Icons.Rounded.Psychology),
    OnboardingContent(R.string.tnqrs_page_2_title, R.string.tnqrs_page_2_description, Icons.Rounded.AutoAwesome),
    OnboardingContent(R.string.tnqrs_page_3_title, R.string.tnqrs_page_3_description, Icons.Rounded.FamilyRestroom)
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: TNQRSOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit
) {
    val saved by viewModel.onboardingSetState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { pages.size })

    LaunchedEffect(saved) {
        if (saved) {
            onNavigateToHomeScreen()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            val page = pages[index]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = page.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(72.dp)
                )
                Spacer(Modifier.height(28.dp))
                Text(
                    text = stringResource(page.title),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = stringResource(page.description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            pages.indices.forEach { index ->
                Spacer(
                    Modifier
                        .size(if (pagerState.currentPage == index) 10.dp else 8.dp)
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
        Spacer(Modifier.height(24.dp))
        if (pagerState.currentPage == pages.lastIndex) {
            Button(
                onClick = viewModel::setOnboarded,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(stringResource(R.string.tnqrs_start_button_title))
            }
        } else {
            Text(
                text = stringResource(R.string.tnqrs_swipe_to_continue),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
