package sikxx.toys.sikxxplaylink.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import sikxx.toys.sikxxplaylink.R
import sikxx.toys.sikxxplaylink.ui.state.DataUiState
import sikxx.toys.sikxxplaylink.ui.viewmodel.CheckoutViewModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToOrdersScreen: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val orderState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalid by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    val enabled by remember {
        derivedStateOf {
            viewModel.customerFirstName.isNotBlank() &&
                viewModel.customerLastName.isNotBlank() &&
                viewModel.customerEmail.isNotBlank()
        }
    }
    if (orderState is DataUiState.Populated) {
        CheckoutDialog(onConfirm = onNavigateToOrdersScreen)
    }
    CheckoutContent(
        firstName = viewModel.customerFirstName,
        lastName = viewModel.customerLastName,
        email = viewModel.customerEmail,
        isEmailInvalid = emailInvalid,
        modifier = modifier,
        focusManager = focusManager,
        enabled = enabled,
        onFirstNameChanged = viewModel::updateCustomerFirstName,
        onLastNameChanged = viewModel::updateCustomerLastName,
        onEmailChanged = viewModel::updateCustomerEmail,
        onPlaceOrder = viewModel::placeOrder
    )
}

@Composable
private fun CheckoutContent(
    firstName: String,
    lastName: String,
    email: String,
    isEmailInvalid: Boolean,
    modifier: Modifier,
    focusManager: FocusManager,
    enabled: Boolean,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPlaceOrder: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(stringResource(R.string.tnqrs_checkout_heading), style = MaterialTheme.typography.headlineMedium)
        Text(stringResource(R.string.tnqrs_checkout_intro), color = MaterialTheme.colorScheme.onSurfaceVariant)
        CheckoutTextField(firstName, onFirstNameChanged, stringResource(R.string.tnqrs_checkout_text_field_first_name), Modifier.fillMaxWidth())
        CheckoutTextField(lastName, onLastNameChanged, stringResource(R.string.tnqrs_checkout_text_field_last_name), Modifier.fillMaxWidth())
        CheckoutTextField(
            input = email,
            onInputChange = onEmailChanged,
            labelText = stringResource(R.string.tnqrs_checkout_text_field_email),
            modifier = Modifier.fillMaxWidth(),
            isError = isEmailInvalid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
        Card(shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(stringResource(R.string.tnqrs_reservation_summary), style = MaterialTheme.typography.titleMedium)
                Text(stringResource(R.string.tnqrs_reservation_note), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Button(
            onClick = onPlaceOrder,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(stringResource(R.string.tnqrs_button_confirm_order_label))
        }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = { Text(labelText) },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        shape = RoundedCornerShape(14.dp)
    )
}
