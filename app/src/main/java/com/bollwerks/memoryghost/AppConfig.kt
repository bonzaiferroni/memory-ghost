package com.bollwerks.memoryghost

import androidx.compose.ui.res.painterResource
import com.bollwerks.eznav.DrawerConfig
import com.bollwerks.eznav.EzConfig

val appConfig = EzConfig(

    drawerConfig = DrawerConfig(
        defaultRoute = AppRoutes.Neuron,
        mainAppIcon = { painterResource(R.drawable.ic_launcher_foreground) },
    )
)