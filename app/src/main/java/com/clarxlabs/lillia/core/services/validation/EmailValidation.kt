package com.clarxlabs.lillia.core.services.validation

import android.util.Patterns
import it.czerwinski.kotlin.util.Either
import it.czerwinski.kotlin.util.Left
import it.czerwinski.kotlin.util.Right

class EmailValidation : TextValidation {

    override fun validate(text: String): Either<Error, String> {
        val isFormatValid = Patterns.EMAIL_ADDRESS.matcher(text).matches()

        if (!isFormatValid) return Left(Error.InvalidFormat)

        return Right(text)
    }

    sealed interface Error : TextValidation.Error {
        data object InvalidFormat : Error
    }
}
