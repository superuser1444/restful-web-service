package com.example.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lombok
@Data // 생성자, Setter, Getter, toString() 자동 생성
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldBean {
    private String message;
}
