package bo.edu.ucb.todo.api;

import bo.edu.ucb.todo.bl.AuthBl;
import bo.edu.ucb.todo.dto.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
class AuthApi {

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<ResponseDto<TokenDto>> login(@RequestBody LoginDto login) {
        ResponseDto<TokenDto> response = new ResponseDto<>();
        AuthBl authBl = new AuthBl();
        TokenDto tokenDto = authBl.login(login);
        int statusCode = 200;
        if (tokenDto == null) {
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Invalid credentials");
            statusCode = 401;
        } else {
            response.setCode("0000");
            response.setResponse(tokenDto);
        }
        return ResponseEntity.status(statusCode).body(response);
    }
}