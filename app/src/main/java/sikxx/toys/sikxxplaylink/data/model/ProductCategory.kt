package sikxx.toys.sikxxplaylink.data.model

import androidx.annotation.StringRes
import sikxx.toys.sikxxplaylink.R

enum class ProductCategory(@field:StringRes val titleRes: Int) {
    BOARD_GAMES(R.string.tnqrs_category_board_games),
    BUILDING_SETS(R.string.tnqrs_category_building_sets),
    PLUSH_TOYS(R.string.tnqrs_category_plush_toys),
    ROBOTS(R.string.tnqrs_category_robots),
    CREATIVE_PLAY(R.string.tnqrs_category_creative_play)
}
