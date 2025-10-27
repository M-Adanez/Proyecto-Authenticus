package es.deusto.sd.proyecto.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public class userDTO {
    @Schema(description = "Username of the User", example = "user1")
    private String username;

    @Schema(description = "Password of the User", example = "password123")
    private String password;

    public userDTO() {
    }

    public userDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
