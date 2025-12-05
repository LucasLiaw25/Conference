package com.liaw.dev.Conference.controller;

import com.liaw.dev.Conference.dto.ConferenceDTO;
import com.liaw.dev.Conference.enums.State;
import com.liaw.dev.Conference.service.ConferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conferences/")
public class ConferenceController {

    private final ConferenceService service;

    @PostMapping
    public ResponseEntity<ConferenceDTO> createConference(@RequestBody @Valid ConferenceDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createConference(dto));
    }

    @GetMapping
    public ResponseEntity<List<ConferenceDTO>> listConference(){
        return ResponseEntity.ok(service.listConference());
    }

    @GetMapping("{id}")
    public ResponseEntity<ConferenceDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("state/{state}")
    public ResponseEntity<List<ConferenceDTO>> findByState(@PathVariable State state){
        return ResponseEntity.ok(service.findByState(state));
    }

    @GetMapping("code/{code}")
    public ResponseEntity<ConferenceDTO> findByCode(@PathVariable String code){
        return ResponseEntity.ok(service.findByCode(code));
    }

    @PutMapping("{id}")
    public ResponseEntity<ConferenceDTO> updateConference(@PathVariable Long id, @RequestBody ConferenceDTO dto){
        return ResponseEntity.ok(service.updateConference(id, dto));
    }

    @DeleteMapping("{id}")
    public Object deleteConference(@PathVariable Long id){
        service.deleteConference(id);
        return new HashMap<>().put("Message", "Conferência deletada com sucesso");
    }

}
