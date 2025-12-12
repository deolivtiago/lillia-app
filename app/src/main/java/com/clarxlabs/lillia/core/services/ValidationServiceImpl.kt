package com.clarxlabs.lillia.core.services

import com.clarxlabs.lillia.core.services.validation.TextFieldValidation
import com.clarxlabs.lillia.core.services.validation.TextFieldValidation.Strategy
import com.clarxlabs.lillia.core.services.validation.TextValidation
import it.czerwinski.kotlin.util.Either

class ValidationServiceImpl : ValidationService {
    override fun validate(text: String, strategy: Strategy): Either<TextValidation.Error, String> =
        TextFieldValidation(strategy).validate(text)
}
