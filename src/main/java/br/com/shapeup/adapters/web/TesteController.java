package br.com.shapeup.adapters.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

  @GetMapping
  public String healthCheck() {
    return                  "UP46";
  }
}
