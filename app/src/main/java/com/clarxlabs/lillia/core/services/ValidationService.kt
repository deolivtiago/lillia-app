package com.clarxlabs.lillia.core.services

import com.clarxlabs.lillia.core.services.validation.TextFieldValidation.Strategy
import com.clarxlabs.lillia.core.services.validation.TextValidation
import it.czerwinski.kotlin.util.Either

interface ValidationService {
    fun validate(text: String, strategy: Strategy): Either<TextValidation.Error, String>
    fun isValid(text: String, strategy: Strategy): Boolean = validate(text, strategy).isRight
    fun isValid(strategyMap: Map<Strategy, String> = emptyMap()): Boolean =
        strategyMap.all { isValid(it.value, it.key) }
}
