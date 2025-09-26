package org.override.docs.presentation.features.warehouse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.override.docs.presentation.theme.DocsTheme

@Composable
fun WarehouseRoot(
    viewModel: WarehouseViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WarehouseScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun WarehouseScreen(
    state: WarehouseState,
    onAction: (WarehouseAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    DocsTheme {
        WarehouseScreen(
            state = WarehouseState(),
            onAction = {}
        )
    }
}