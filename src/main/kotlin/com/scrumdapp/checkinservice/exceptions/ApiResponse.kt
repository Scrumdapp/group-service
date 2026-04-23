package com.scrumdapp.checkinservice.exceptions

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse(val code: Int, val message: String, val stackTrace: String? = null)