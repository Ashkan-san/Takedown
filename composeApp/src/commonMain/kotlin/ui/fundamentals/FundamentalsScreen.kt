package ui.fundamentals

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import commons.navigation.NavItem
import commons.ui.TakedownScreen
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@Composable
fun FundamentalsScreen(
        modifier: Modifier = Modifier,
        viewModel: FundamentalsViewModel = koinInject(),
        navigator: Navigator,
        onClickCard: (String) -> Unit = {},
) {
    TakedownScreen(
        title = NavItem.Fundamentals.title,
        navigator = navigator
    ) {
        innerPadding ->
        FundamentalsContent(
            fundamentalsList = viewModel.fundamentalsList,
            onClickCard = onClickCard,
            modifier = modifier
                .padding(innerPadding)
        )
    }
}