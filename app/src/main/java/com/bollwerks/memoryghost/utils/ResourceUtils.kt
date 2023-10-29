package com.bollwerks.memoryghost.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.bollwerks.memoryghost.R

object Paddings {
    @Composable
    fun small(): Dp = dimensionResource(R.dimen.padding_small)

    @Composable
    fun medium(): Dp = dimensionResource(R.dimen.padding_medium)

    @Composable
    fun large(): Dp = dimensionResource(R.dimen.padding_large)
}

object Gaps {
    @Composable
    fun small(): Dp = dimensionResource(R.dimen.gap_small)

    @Composable
    fun medium(): Dp = dimensionResource(R.dimen.gap_medium)

    @Composable
    fun large(): Dp = dimensionResource(R.dimen.gap_large)
}