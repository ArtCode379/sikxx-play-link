package sikxx.toys.sikxxplaylink.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import sikxx.toys.sikxxplaylink.R

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val supportUrl = stringResource(R.string.tnqrs_customer_support_link)
    Column(
        modifier = modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(stringResource(R.string.tnqrs_top_bar_settings_title), style = MaterialTheme.typography.headlineMedium)
        Card(shape = RoundedCornerShape(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Icon(Icons.Rounded.Storefront, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Column {
                    Text(stringResource(R.string.tnqrs_company_name), style = MaterialTheme.typography.titleMedium)
                    Text(
                        stringResource(R.string.tnqrs_version_value, stringResource(R.string.tnqrs_app_version)),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        Text(stringResource(R.string.tnqrs_support_heading), style = MaterialTheme.typography.titleLarge)
        Text(stringResource(R.string.tnqrs_support_copy), color = MaterialTheme.colorScheme.onSurfaceVariant)
        Button(
            onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(supportUrl)))
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Rounded.OpenInNew, contentDescription = null)
            Text(stringResource(R.string.tnqrs_settings_screen_customer_support_label), modifier = Modifier.padding(start = 8.dp))
        }
    }
}
