package com.clarxlabs.lillia.ui.users.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clarxlabs.lillia.ui.AppRoute
import com.clarxlabs.lillia.ui.theme.LilliaTheme

@Composable
fun ListView(viewModel: ListViewModel, onNavigate: (AppRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::sendEvent

    ListViewContent(state, onEvent, onNavigate)
}

@Composable
fun ListViewContent(
    state: ListModel.State,
    onEvent: (ListModel.Event) -> Unit,
    onNavigate: (AppRoute) -> Unit,
) {
    Surface(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
    ) {
        if (state.isLoading) Box(contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        else LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            items(state.users, key = { it.id }) {
                Card {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Nome:")
                            Text(it.fullName)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Email:")
                            Text(it.email)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                it.id,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.outline,
                                )

                            )
                        }
                    }
                }
            }
        }
    }

}

@Preview(showSystemUi = true, device = "spec:parent=pixel_3a")
@Composable
fun PreviewPhone() {
    LilliaTheme {
        ListViewContent(
            state = ListModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(showSystemUi = true, device = "spec:width=720px,height=1280px,dpi=320,navigation=buttons")
@Composable
fun PreviewPhoneSmall() {
    LilliaTheme {
        ListViewContent(
            state = ListModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletPortrait() {
    LilliaTheme {
        ListViewContent(
            state = ListModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletLandscape() {
    LilliaTheme {
        ListViewContent(
            state = ListModel.State(),
            onEvent = {},
            onNavigate = {},
        )
    }
}
