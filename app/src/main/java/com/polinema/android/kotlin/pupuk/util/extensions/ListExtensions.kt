package com.paulrybitskyi.sample.utils.extensions

import java.util.*


fun <E> List<E>.random(random: Random): E? {
    return if (size > 0) get(random.nextInt(size)) else null
}