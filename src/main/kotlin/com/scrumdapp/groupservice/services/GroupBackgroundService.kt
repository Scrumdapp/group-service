package com.scrumdapp.groupservice.services

enum class GroupBackgroundService(
    val value: String
) {
    BACKGROUND_1("7_1"),
    BACKGROUND_1_2("7_1"),
    BACKGROUND_2("7_1"),
    BACKGROUND_4("7_1"),
    BACKGROUND_5("7_1"),
    BACKGROUND_6("7_1"),
    BACKGROUND_6_2("7_1"),
    BACKGROUND_7("7_1"),
    BACKGROUND_7_2("7_2"),
    BACKGROUND_8("8"),
    BACKGROUND_9("9"),
    BACKGROUND_10("10"),
    BACKGROUND_14("14"),
    BACKGROUND_14_2("14_2"),
    BACKGROUND_15("15"),
    BACKGROUND_17("17"),
    BACKGROUND_18("18"),
    BACKGROUND_22("22"),
    BACKGROUND_23("23"),
    BACKGROUND_30("30"),
    BACKGROUND_COLOR_AQUA("color_aqua"),
    BACKGROUND_COLOR_BG("color_bg"),
    BACKGROUND_COLOR_BLUE("color_blue"),
    BACKGROUND_COLOR_GRAY("color_gray"),
    BACKGROUND_COLOR_GREEN("color_green"),
    BACKGROUND_COLOR_ORANGE("color_orange"),
    BACKGROUND_COLOR_PURPLE("color_purple"),
    BACKGROUND_COLOR_RED("color_red");

    companion object {
        fun exists(value: String): Boolean =
            entries.any { it.value == value }
    }
}
