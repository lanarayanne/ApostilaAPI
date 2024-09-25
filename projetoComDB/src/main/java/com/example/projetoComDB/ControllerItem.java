package com.example.projetoComDB;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
@CrossOrigin(origins = "http://127.0.0.1:5500")

public class ControllerItem {

    private final ItemRepository itemRepository;

    public ControllerItem(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    /*MÉTODO CREATE*/
    @PostMapping("/item/create")
    public ResponseEntity<Item> create (@RequestBody Item item){

        if (item == null || item.getName() == null || item.getPrice() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Dados inválidos
        }

        return ResponseEntity.status(HttpStatus.OK).body(itemRepository.save(item));
    }

    /*MÉTODO FINDBY*/

    @GetMapping("/item/{id}")
    public ResponseEntity<Item> findById (@PathVariable Long id){
        Optional<Item> itemOptional = itemRepository.findById(id);

        if (itemOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(itemOptional.get());
        }
        else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*MÉTODO FINDALL*/

    @GetMapping("/item/all")
    public ResponseEntity<List<Item>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(itemRepository.findAll());
    }

    /*MÉTODO UPDATE*/

    @PostMapping("/item/update")
    public ResponseEntity<Item> update(@RequestBody Item item){
        Optional<Item> itemOptional = itemRepository.findById(item.getId());
        if (itemOptional.isPresent()) {
            Item myItem = itemOptional.get();

            myItem.setName(item.getName());
            myItem.setPrice(item.getPrice());

            return ResponseEntity.status(HttpStatus.OK).body(itemRepository.save(myItem));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*MÉTODO DELETE*/

    @DeleteMapping("/item/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        itemRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT). build();
    }
}
