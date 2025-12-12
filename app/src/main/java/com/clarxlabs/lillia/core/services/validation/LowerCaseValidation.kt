package com.clarxlabs.lillia.core.services.validation

import it.czerwinski.kotlin.util.Either
import it.czerwinski.kotlin.util.Left
import it.czerwinski.kotlin.util.Right


class LowerCaseValidation(val min: Int = 1) : TextValidation {

    override fun validate(text: String): Either<Error, String> {
        val lowers = text.filter { it.isLowerCase() }

        if (lowers.length < min) return Left(Error.AtLeast(min))

        return Right(text)
    }

    sealed interface Error : TextValidation.Error {
        data class AtLeast(val min: Int) : Error
    }
}
