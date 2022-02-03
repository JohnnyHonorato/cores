package br.edu.ufcg.virtus.tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("system")
@Api(value = "system", tags = "system-controller")
public class SystemController {

    
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck()  {
        return new ResponseEntity<>("HEALTHY", HttpStatus.OK);
    }
    
}
