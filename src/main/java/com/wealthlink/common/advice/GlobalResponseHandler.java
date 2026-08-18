package com.wealthlink.common.advice;

import com.wealthlink.common.dto.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Apply this advice to all controllers
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        
        String path = request.getURI().getPath();
        
        // Don't wrap responses for swagger/openapi or actuator
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui") || path.startsWith("/actuator")) {
            return body;
        }

        // If the body is already an ApiResponse, don't wrap it again
        if (body instanceof ApiResponse) {
            return body;
        }

        // If the body is a string, Spring's StringHttpMessageConverter will try to cast our ApiResponse to String and fail.
        // It's a known Spring issue. To fix it, we either have to configure the converters or handle String separately.
        // For simplicity, we just wrap it. If there are String-returning APIs, this might need a json serialization step here.
        // Assuming most APIs return objects.
        return ApiResponse.success(body);
    }
}
