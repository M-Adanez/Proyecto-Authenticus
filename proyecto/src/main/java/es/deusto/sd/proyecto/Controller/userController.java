package es.deusto.sd.proyecto.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Service.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/user")
@Tag(name="User", description = "Different Actions for user")
public class userController {
    private final userService userService;

    public userController(userService userService){
        this.userService=userService;
    }


    //LOGIN
    @RequestMapping("/login")
    @Operation(
        summary = "Login to the App",
        description = "Returns a Token to use the app"
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @ApiResponse(responseCode = "401", description = "Contraseña Erronea")
    
    @PostMapping
    public ResponseEntity<String> getToken(
        @Parameter(description="Username and password",required = true)
        @RequestBody userDTO userDTO) {
        
        //CAMBIAR CONDICIONES
        
        if(true){ //USUARIO NO ENCONTRADO
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
        }else if(true){ //CONTRASEÑA INCORRECTA
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//401
            }

        String token=userService.generateToken(userDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);//200
    }

    //REGISTER

    @RequestMapping("/register")
    @Operation(
        summary = "Register in the App",
        description = "Creates a User for future Logins"
    )
    @ApiResponse(responseCode = "200", description = "OK")

    @PostMapping
    public ResponseEntity<String> register(
        @Parameter(description="Username and password",required = true)
        @RequestBody userDTO userDTO) {

        userService.registerUser(userDTO);
            
        return new ResponseEntity<>(HttpStatus.OK);//200
        }


    //LOGOUT


}
