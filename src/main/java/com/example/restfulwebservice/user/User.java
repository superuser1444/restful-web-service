package com.example.restfulwebservice.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value={"password", "ssn"})
//@JsonFilter("UserInfo") //UserInfo : 임의의 이름
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {
    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    private String name;

    @Past // 미래의 date를 사용할 수  없고, 과거의 date만 사용가능
    @ApiModelProperty(notes = "사용자 등록일을 입력해 주세요.")
    private Date joinDate;

    // @JsonIgnore
    @ApiModelProperty(notes = "사용자 패스워드를 입력해 주세요.")
    private String password;

    // @JsonIgnore
    @ApiModelProperty(notes = "사용자 주민번호를 입력해 주세요.")
    private String ssn;
}
