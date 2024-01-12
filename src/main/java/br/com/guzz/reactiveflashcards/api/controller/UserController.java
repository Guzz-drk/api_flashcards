package br.com.guzz.reactiveflashcards.api.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.guzz.reactiveflashcards.api.controller.request.UserRequest;
import br.com.guzz.reactiveflashcards.api.controller.response.UserResponse;
import br.com.guzz.reactiveflashcards.api.mapper.UserMapper;
import br.com.guzz.reactiveflashcards.core.validation.MongoId;
import br.com.guzz.reactiveflashcards.domain.service.UserService;
import br.com.guzz.reactiveflashcards.domain.service.query.UserQueryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Validated
@RestController
@RequestMapping("users")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserQueryService userQueryService;
    private final UserMapper userMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<UserResponse> save(@Valid @RequestBody final UserRequest userRequest) {
        return userService.save(userMapper.toDocument(userRequest))
                .doFirst(() -> log.info("=== Saving a user with follow data {} ", userRequest))
                .map(userMapper::toResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<UserResponse> findById(
            @MongoId(message = "{userController.id}") @PathVariable(value = "id") @Valid final String id) {
        return userQueryService.findById(id)
                .doFirst(() -> log.info("=== Finding a user with follow id {}", id))
                .map(userMapper::toResponse);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<UserResponse> update(
            @MongoId(message = "{userController.id}") @PathVariable(value = "id") @Valid final String id,
            @Valid @RequestBody final UserRequest userRequest) {
                return userService.update(userMapper.toDocument(userRequest, id))
                .doFirst(() -> log.info("=== Updating a user with follow info [body: {}, if: {}]", userRequest, id))
                .map(userMapper::toResponse);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> delete(@MongoId(message = "{userController.id}") @PathVariable(value = "id") @Valid final String id){
        return userService.delete(id)
            .doFirst(() -> log.info("=== Deleting a user with follow id {}", id));
    }

}