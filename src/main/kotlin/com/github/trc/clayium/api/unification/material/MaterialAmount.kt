package com.github.trc.clayium.api.unification.material

import com.github.trc.clayium.api.M

@JvmInline
value class MaterialAmount private constructor(val raw: Long) {

    val dustAmount get() = (raw / M).toInt()

    companion object {
        val NONE = MaterialAmount(-1)
        fun of(dustAmount: Long) = MaterialAmount(M * dustAmount)
        fun nugget(nuggetAmount: Long) = MaterialAmount((M / 9) * nuggetAmount)

        /**
         * use this only when you are sure that the amount is correct.
         */
        fun createRaw(raw: Long) = MaterialAmount(raw)
    }
}