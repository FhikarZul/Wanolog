package com.randev.movieapp_kmm.android.composable.components.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import com.randev.wanolog.android.R
import com.randev.wanolog.android.utils.emptyString

/**
 * @author Raihan Arman
 * @date 12/10/22
 */

@Composable
fun BaseImageView(
    modifier: Modifier = Modifier,
    imageResourceId: Int,
    contentScale: ContentScale? = null
) {
    Image(
        painter = painterResource(imageResourceId),
        contentDescription = emptyString(),
        modifier = modifier,
        contentScale = contentScale ?: ContentScale.FillBounds
    )
}

@Composable
fun BaseImageView(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale? = null,
) {
    SubcomposeAsyncImage(
        model = url,
        modifier = modifier,
        loading = {
            DefaultLoadingView()
        },
        error = {
            Image(painter = painterResource(id = R.drawable.ic_placeholder), contentDescription = null)
        },
        success = {
            val state = painter.state
            if (state is AsyncImagePainter.State.Success) {
                Image(
                    painter = this.painter, contentDescription = emptyString(),
                    modifier = modifier,
                    contentScale = contentScale ?: ContentScale.FillBounds,
                )
            }


        },
        contentDescription = emptyString()
    )
}