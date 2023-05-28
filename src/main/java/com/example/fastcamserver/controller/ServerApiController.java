package com.example.fastcamserver.controller;

import com.example.fastcamserver.dto.Req;
import com.example.fastcamserver.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api/server")
@Slf4j
public class ServerApiController {


    @GetMapping("/naver")
    public String naver() {

        String query = "갈비집";

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", query)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        log.info("uri : {} " + uri);

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "Jlb3guWfMgFw4cXS7dEx")
                .header("X-Naver-Client-Secret", "jum3fLD9Gb")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        return result.getBody();
    }


    @GetMapping("/hello")
    public User hello(@RequestParam String name, @RequestParam int age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return user;
    }

//    @PostMapping("/user/{userId}/name/{userName}")
//    public User post(@RequestBody User user,
//                     @PathVariable int userId,
//                     @PathVariable String userName,
//                     @RequestHeader("x-authorization") String authorization,
//                     @RequestHeader("custom-header") String customHeader
//
//    ) {
//        log.info("userId :{}, userName : {}", userId, userName);
//        log.info("authorization :{}, customHeader : {}", authorization, customHeader);
//        log.info("client req:{}", user);
//        return user;
//    }

    @PostMapping("/user/{userId}/name/{userName}")
    public Req<User> post(
//            HttpEntity<String> entity,
            @RequestBody Req<User> user,
            @PathVariable int userId,
            @PathVariable String userName,
            @RequestHeader("x-authorization") String authorization,
            @RequestHeader("custom-header") String customHeader

    ) {
//        log.info("req : {}", entity.getBody());
        log.info("userId :{}, userName : {}", userId, userName);
        log.info("authorization :{}, customHeader : {}", authorization, customHeader);
        log.info("client req:{}", user);

        Req<User> response = new Req<>();
        response.setHeader(
                new Req.Header()
        );
        response.setResBody(user.getResBody());
        return user;
    }
}
