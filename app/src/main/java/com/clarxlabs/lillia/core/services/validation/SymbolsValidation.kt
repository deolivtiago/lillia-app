package com.clarxlabs.lillia.core.services.validation

import it.czerwinski.kotlin.util.Either
import it.czerwinski.kotlin.util.Left
import it.czerwinski.kotlin.util.Right


class SymbolsValidation(
    val permitted: String = ".!?@#%^&*_+-$,",
    val min: Int = 1,
) : TextValidation {

    override fun validate(text: String): Either<Error, String> {
        val symbols = text.filter { permitted.contains(it) }

        if (symbols.length < min) return Left(Error.AtLeast(min, permitted))

        return Right(text)
    }

    sealed interface Error : TextValidation.Error {
        data class AtLeast(val min: Int, val permitted: String = ".!?@#%^&*_+-$,") : Error
    }
}
