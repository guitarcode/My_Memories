package toyproject.memories.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class Result<T> {
    private SuccessOrFail result;
    private T data;
}

