package org.booking.common

abstract class ACommandHandler<IN, OUT> {
    abstract fun validate(payload: IN)

    protected abstract fun processInternal(payload: IN): OUT

    operator fun invoke(payload: IN): OUT {
        validate(payload)
        return processInternal(payload)
    }
}