package org.booking.common.extensions

import reactor.util.function.Tuple2

object Types {

    operator fun <A, B> Tuple2<A, B>.component1(): A {
        return this.t1
    }

    operator fun <A, B> Tuple2<A, B>.component2(): B {
        return this.t2
    }
}