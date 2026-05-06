package com.aurandri.realtimeordertracker.CustomHandling;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

// @JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Resp<T> {
    private Integer code;
    private String message;
    private T data;
}
