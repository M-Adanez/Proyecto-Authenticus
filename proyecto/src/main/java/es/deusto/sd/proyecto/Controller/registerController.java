package es.deusto.sd.proyecto.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Service.registerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/register")
@Tag(name="Register", description = "Registers user in the App")
public class registerController {
        private final registerService registerService;

        public registerController(registerService registerService){
            this.registerService=registerService;
        }


    @Operation(
        summary = "Register in the App",
        description = "Creates a User for future Logins"
    )
    @ApiResponse(responseCode = "200", description = "OK")

        @PostMapping
        public ResponseEntity<String> register(
            @Parameter(description="Username and password",required = true)
            @RequestBody userDTO userDTO) {

                registerService.registerUser(userDTO);
            
            
            return new ResponseEntity<>(HttpStatus.OK);//200
        }
        

}
