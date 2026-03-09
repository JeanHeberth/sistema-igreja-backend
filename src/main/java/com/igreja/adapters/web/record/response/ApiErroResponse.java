package com.igreja.adapters.web.record.response;

public record ApiErroResponse(
    int status,
    String error,
    String message,
    String path
) {}
