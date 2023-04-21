package com.tochanenko.khtoneskache

enum class Muscle(
    val id: Int,
    val title: String,
    val image: String
) {
    ARM(
        1,
        "Arm",
        ""
    ),
    SHOULDER(
        2,
        "Shoulder",
        ""
    ),
    TIT(
        3,
        "Tit",
        ""
    ),
    PRESS(
        4,
        "Press",
        ""
    ),
    BACK(
        5,
        "Back",
        ""
    ),
    BUTT(
        6,
        "Butt",
        ""
    ),
    LEG(
        7,
        "Leg",
        ""
    ),
    SHIN(
        8,
        "Shin",
        ""
    );

    companion object {
        fun fromId(value: Int): Muscle = enumValues<Muscle>().first { it.id == value }
    }
}
