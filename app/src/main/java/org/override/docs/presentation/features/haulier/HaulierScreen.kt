package org.override.docs.presentation.features.haulier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.override.docs.presentation.theme.DocsTheme

@Composable
fun HaulierRoot(
    viewModel: HaulierViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HaulierScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun HaulierScreen(
    state: HaulierState,
    onAction: (HaulierAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    DocsTheme {
        HaulierScreen(
            state = HaulierState(),
            onAction = {}
        )
    }
}