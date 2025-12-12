package com.clarxlabs.lillia.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clarxlabs.lillia.ui.AppRoute
import com.clarxlabs.lillia.ui.components.ActionButton
import com.clarxlabs.lillia.ui.components.TextFormField
import com.clarxlabs.lillia.ui.theme.LilliaTheme
import kotlinx.coroutines.launch


@Composable
fun HomeView(viewModel: HomeViewModel, navigateTo: (AppRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sendEvent = viewModel::sendEvent

    HomeViewContent(state, sendEvent, navigateTo)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewContent(
    state: HomeModel.State,
    sendEvent: (HomeModel.Event) -> Unit,
    navigateTo: (AppRoute) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(
            drawerState = drawerState,
            drawerContainerColor = MaterialTheme.colorScheme.primary,
            drawerContentColor = MaterialTheme.colorScheme.onPrimary,
            drawerShape = MaterialTheme.shapes.extraSmall,
        ) {
            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Olá, ${state.user.fullName.split(" ").first()}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimary,
                        ),
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                selected = false,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                colors = NavigationDrawerItemDefaults.colors(),
                onClick = {
                    scope.launch { drawerState.close() }
                },
            )

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 8.dp)
                    .weight(1F),
            ) {
                (1..1).toList().forEach {
                    NavigationDrawerItem(
                        label = { Text(text = "Cadastro") },
                        icon = {
                            Icon(imageVector = Icons.Default.Person, contentDescription = "")
                        },
                        selected = it == 2,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(),
                        onClick = {
                            sendEvent(HomeModel.Event.OnProfileClicked(navigateTo))
                            scope.launch { drawerState.close() }
                        },
                        shape = MaterialTheme.shapes.large,
                    )
                }
            }

            Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.onPrimary)) {
                ActionButton(
                    actionTitle = "Desconectar",
                    onClicked = {
                        scope.launch {
                            drawerState.close()
                            sendEvent(HomeModel.Event.OnSignOutClicked(navigateTo))
                        }
                    },
                    modifier = Modifier.padding(8.dp),
                )
//                NavigationDrawerItem(
//                    label = {
//                        Text(
//                            text = "Desconectar",
//                            style = MaterialTheme.typography.titleMedium.copy(
//                                fontWeight = FontWeight.Bold,
//                                color = MaterialTheme.colorScheme.onPrimary,
//                            ),
//                        )
//                    },
//                    icon = {
//                        Icon(
//                            imageVector = Icons.Default.Output,
//                            contentDescription = "",
//                            tint = MaterialTheme.colorScheme.onPrimary,
//                        )
//                    },
//                    selected = false,
//                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
////                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
//                    colors = NavigationDrawerItemDefaults.colors(),
//                    onClick = {
//                        scope.launch {
//                            drawerState.close()
//                            onEvent(HomeModel.Event.OnSignOutClicked(onNavigate))
//                        }
//                    },
//                    shape = MaterialTheme.shapes.large,
//                )
            }
        }
    }) {
        Scaffold(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxSize(),

            topBar = {
                TopAppBar(
                    title = { Text("Lillia") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu icon",
                                tint = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    })
            }) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(it)
                    .padding(8.dp)
                    .imePadding()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Text(
                        text = "Access token: ${state.accessToken}",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = "Refresh token: ${state.refreshToken}",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    TextFormField(
                        value = state.email,
                        valueErrorMessage = state.emailError,
                        onValueChanged = {
                            sendEvent(HomeModel.Event.OnEmailChanged(it))
                        },
                        isLoading = state.isLoading,
                    )

                    ActionButton(
                        onClicked = { sendEvent(HomeModel.Event.OnSubmitClicked(navigateTo)) },
                        isLoading = state.isLoading,
                    )
                }


            }
        }
    }
}

@Preview(showSystemUi = true, device = "spec:parent=pixel_3a")
@Composable
fun HomePreviewPhone() {
    LilliaTheme {
        HomeViewContent(
            state = HomeModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(showSystemUi = true, device = "spec:parent=Galaxy Nexus,navigation=buttons")
@Composable
fun HomePreviewPhoneSmall() {
    LilliaTheme {
        HomeViewContent(
            state = HomeModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun HomePreviewTabletPortrait() {
    LilliaTheme {
        HomeViewContent(
            state = HomeModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun HomePreviewTabletLandscape() {
    LilliaTheme {
        HomeViewContent(
            state = HomeModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}
