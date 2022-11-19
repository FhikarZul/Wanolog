package com.randev.wanolog.android.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.randev.domain.model.AnimeListModel
import com.randev.movieapp_kmm.android.composable.components.space.VerticalSpacer
import com.randev.wanolog.android.composable.components.header.Header

/**
 * @author Raihan Arman
 * @date 17/11/22
 */

@Composable
fun AnimeTrendingSection(
    modifier: Modifier = Modifier,
    data: List<AnimeListModel.AnimeModel>
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {

        VerticalSpacer(height = 20.dp)
        Header(
            text = "Trending"
        )
        VerticalSpacer(height = 10.dp)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(data) {
                ItemCard(
                    title = it.attributes.titles.enJp,
                    image = it.attributes.posterImage.original,
                    modifier = Modifier
                        .width(150.dp)
                )
            }
        }
    }
}