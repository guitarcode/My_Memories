package toyproject.memories.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class Result<T> {
    private resultEnum result;
    private T data;
}

