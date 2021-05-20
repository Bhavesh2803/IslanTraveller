package com.islantraveller.Network.rest;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils
{
    public static APIErrors parseError(Response<?> response) {
        Converter<ResponseBody, APIErrors> converter = ServiceGenerator.getRetrofit().responseBodyConverter(APIErrors.class, new Annotation[0]);
        APIErrors error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIErrors();
        }

        return error;
    }
}
