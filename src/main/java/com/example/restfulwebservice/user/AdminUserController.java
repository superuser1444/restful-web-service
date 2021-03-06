package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users") // <- users: endpoint
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = service.findAll();

        // SimpleBeanPropertyFilter : Bean의 Property를 제어
        //포함시키고자 하는 필터 값
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");
        // id : 필터가 어떠한 Bean을 대상으로 사용될지 필터의 이름을 적어줘야 하는데 이에 해당
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // 클라이언트에 반환하는 Object 타입이 User가 아닌 필터를 적용할 수 있는 MappingJacksonValue 타입으로 변경
        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    // GET /users/1 or /users/10 -> String
    //@GetMapping("/v1/users/{id}")
//    @GetMapping(value = "/users/{id}/", params = "version=1")
    @GetMapping(value = "/users/{id}", headers="X-API-VERSION=1")
//    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // SimpleBeanPropertyFilter : Bean의 Property를 제어
        //포함시키고자 하는 필터 값
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");
        // id : 필터가 어떠한 Bean을 대상으로 사용될지 필터의 이름을 적어줘야 하는데 이에 해당
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // 클라이언트에 반환하는 Object 타입이 User가 아닌 필터를 적용할 수 있는 MappingJacksonValue 타입으로 변경
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

    //@GetMapping("/v2/users/{id}")
//    @GetMapping(value = "/users/{id}/", params = "version=2")
    @GetMapping(value = "/users/{id}", headers="X-API-VERSION=2")
//    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        // SimpleBeanPropertyFilter : Bean의 Property를 제어
        //포함시키고자 하는 필터 값
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");
        // id : 필터가 어떠한 Bean을 대상으로 사용될지 필터의 이름을 적어줘야 하는데 이에 해당
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        // 클라이언트에 반환하는 Object 타입이 User가 아닌 필터를 적용할 수 있는 MappingJacksonValue 타입으로 변경
        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;
    }
}
