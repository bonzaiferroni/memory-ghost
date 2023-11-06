package com.bollwerks.memoryghost.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.bollwerks.memoryghost.ui.theme.MemoryGhostTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String,
    modifier: Modifier = Modifier,
    drawerState: DrawerState? = null,
    fabParams: FabParams? = null,
    titleContent: @Composable (() -> Unit)? = null,
    menuItems: List<MoreMenuItem>? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            // to run the animation independently
            val coroutineScope = rememberCoroutineScope()
            CenterAlignedTopAppBar(
                title = titleContent ?: {
                    Text(title)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            // opens drawer
                            drawerState?.open()
                        }
                    }) {
                        Icon(
                            // internal hamburger menu
                            Icons.Rounded.Menu,
                            contentDescription = "MenuButton"
                        )
                    }
                },
                actions = {
                    if (menuItems != null) {
                        MoreMenu(items = menuItems)
                    }
                },
            )
        },
        floatingActionButton = {
            fabParams?.let {
                FloatingActionButton(onClick = it.onClick) {
                    Icon(it.icon, contentDescription = it.contentDescription)
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Surface {
            // padding of the scaffold is enforced to be used
            Column(modifier = Modifier.padding(paddingValues)) {
                content()
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppScaffoldPreview() {
    MemoryGhostTheme {
        AppScaffold(
            title = "Title",
            menuItems = listOf(
                MoreMenuItem("Item 1") {},
                MoreMenuItem("Item 2") {},
                MoreMenuItem("Item 3") {},
            )
        ) {
            Text("Content")
        }
    }
}

data class FabParams(
    val icon: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit
)