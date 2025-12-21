package es.deusto.sd.proyecto.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Service.APIResponse;
import es.deusto.sd.proyecto.Service.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/user")
@Tag(name="User", description = "Different Actions for user")
public class userController {
    private final userService userService;

    public userController(userService userService){
        this.userService=userService;
    }


    //LOGIN
    @Operation(
        summary = "Login to the App",
        description = "Returns a Token to use the app"
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @ApiResponse(responseCode = "401", description = "Contraseña Erronea")
    
    @PostMapping("/login")
    public ResponseEntity<String> login(
        //CUANDO HACES LOGIN LUEGO REMOVE LUEGO REGISTER YA NO PUEDES HACER LOGIN OTRA VEZ "CON EL MISMO USUARIO"
        @Parameter(description="Username and password",required = true)
        @RequestBody userDTO userDTO) {
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Bad Request
        }

        APIResponse resp=userService.loginUser(userDTO);


        switch (resp) {
            case APIResponse.FALTAN_DATOS:
                return new ResponseEntity<>(resp.getStatus()); //400 faltan datos
            case APIResponse.CONTRASEÑA_MAL:
                return new ResponseEntity<>(resp.getStatus());//401 contraseña mal
            
            case APIResponse.NO_EXISTE:
                return new ResponseEntity<>(resp.getStatus());//404 no existe

            case APIResponse.YA_LOGEADO:
                return new ResponseEntity<>(resp.getStatus());//409 ya logeado                

            case APIResponse.TOKEN:
                return new ResponseEntity<>(resp.getMensaje(), resp.getStatus());//200 ok                

            default:
                break;

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //REGISTER

    @Operation(
        summary = "Registrarse",
        description = "Crea usuario para logins"
    )
    @ApiResponse(responseCode = "201", description = "Creado")
    @ApiResponse(responseCode = "400", description = "Faltan Datos")
    @ApiResponse(responseCode = "409", description = "Usuario ya registrado")


    @PostMapping("/register")
    public ResponseEntity<String> register(
        @RequestBody userDTO userDTO) {//TODS LOS CAMPOS SON NULL ENTONCES DA ERROR PERO SOLO AL USAR EL BODY, NO PARAMS
        APIResponse reg=userService.registerUser(userDTO);

        reg=(APIResponse)reg;
        switch (reg) {
            case APIResponse.FALTAN_DATOS:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400 falta informacion
            
            case APIResponse.YA_REGISTRADO:
                return new ResponseEntity<>(HttpStatus.CONFLICT);//409 ya esta registrado
            
            case APIResponse.CREADO:
                return new ResponseEntity<>(HttpStatus.CREATED);// 201 CREATED
        
            default:
                break;
        }
            
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//200
        }


    @Operation(
        summary = "Cerrar Sesion",
        description = "Cierra la Sesión del Usuario"
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "No hay sesion iniciada")

    @DeleteMapping("/logout/{token}")
    public ResponseEntity<String> logout(
        @Parameter(description = "User Token", required = true)
        @PathVariable("token") String token){
        APIResponse la=userService.logout(token);
        
        switch (la) {
            case APIResponse.BIEN:
                return new ResponseEntity<>(HttpStatus.OK); //200

            case APIResponse.MAL:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400

            default:
                break;
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400
    }

    @Operation(
        summary = "Eliminar Usuario",
        description = "Elimina un Usuario del Sistema"
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "No hay sesion iniciada")
    @ApiResponse(responseCode = "404", description = "No se ha encontrado usuario")

     @DeleteMapping("/remove/{token}")
    public ResponseEntity<String> remove(
        @Parameter(description = "User Token", required = true)
        @PathVariable("token") String token){
        APIResponse la=userService.remove(token);
        
        switch (la) {
            case APIResponse.BIEN:
                return new ResponseEntity<>(HttpStatus.OK); //200

            case APIResponse.NO_EXISTE:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404

            default:
                break;
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400
    }
}
