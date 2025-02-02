package com.randev.wanolog.android.presentation.anime_detail

import com.randev.domain.model.AnimeDetailModel
import com.randev.domain.model.AnimeListModel

/**
 * @author Raihan Arman
 * @date 26/11/22
 */
data class DetailAnimeState(
    val data: AnimeDetailModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
