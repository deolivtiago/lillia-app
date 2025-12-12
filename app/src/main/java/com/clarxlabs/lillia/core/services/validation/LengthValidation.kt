package com.clarxlabs.lillia.core.services.validation

import it.czerwinski.kotlin.util.Either
import it.czerwinski.kotlin.util.Left
import it.czerwinski.kotlin.util.Right

class LengthValidation(
    val min: Int = 6, val max: Int = 160, val isRequired: Boolean = false
) : TextValidation {

    override fun validate(text: String): Either<Error, String> {
        if (isRequired and text.isBlank()) return Left(Error.Required)

        if (text.isNotBlank()) {
            if (text.length < min) return Left(Error.TooShort(min))
            if (text.length > max) return Left(Error.TooLong(max))
        }

        return Right(text)
    }

    sealed interface Error : TextValidation.Error {
        data object Required : Error
        data class TooShort(val min: Int) : Error
        data class TooLong(val max: Int) : Error
    }
}
