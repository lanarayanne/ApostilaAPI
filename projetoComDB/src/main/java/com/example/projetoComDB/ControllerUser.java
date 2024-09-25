package com.example.projetoComDB;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")

public class ControllerUser {

    private final UserRepository userRepository; //Chama a interface UserRepository

    public ControllerUser(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /*MÉTODO CREATE*/
    @PostMapping("/user/create") //Mapeia requisiçoes POST para o endpoint /create
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(user));
    }
    //Request body - indica que as informações serão recebidas através do corpo de requisição

    /*MÉTODO FINDBY*/

    @GetMapping("/user/{id}") //Mapeia requisições do tipo GET para url /{id}
    public ResponseEntity<User> findById (@PathVariable Long id){
        //PathVariable -espera uma variável que será atribuída ao id
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
        }
        else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*MÉTODO FINDALL - retornar todas as entidades do tipo User*/

    @GetMapping("/user/all")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    /*MÉTODO UPDATE - Mapeia requisiç~oes do tipo POST para a url  /update */

    @PostMapping("/user/update")
    public ResponseEntity<User> update (@RequestBody User user){
        Optional<User> userOptional = userRepository.findById(user.getId());

        if (userOptional.isPresent()) {
            User myUser = userOptional.get();

            myUser.setName(user.getName());
            myUser.setPassword(user.getPassword());
            myUser.setEmail(user.getEmail());

            return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(myUser));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*MÉTODO DELETE*/

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT). build();
    }


}
