package com.example.projetoComDB;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping //designa endpoint inicial para a classe PedidoController
@CrossOrigin(origins = "http://127.0.0.1:5500")

public class ControllerPedido {
    private final PedidoRepository pedidoRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ControllerPedido(PedidoRepository pedidoRepository,
                           UserRepository userRepository,
                           ItemRepository itemRepository) {
        this.pedidoRepository = pedidoRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    /*MÉTODO CREATE*/
    /*
    @PostMapping("/pedido/create")
        public ResponseEntity<Pedido> create (@RequestBody Pedido pedido, @RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Usuário não encontrado
        }

        return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.save(
                new Pedido (pedido.getName(), userOptional.get())));
    }
    */

    @PostMapping("/pedido/create")
    public ResponseEntity<Pedido> create (@RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.save(
                new Pedido (userOptional.get())));
    }

    /*MÉTODO FINDBY*/

    @GetMapping("/pedido/{id}")
    public ResponseEntity<Pedido> findById (@PathVariable Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(pedidoOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*MÉTODO FINDALL*/
    @GetMapping("/pedido/all")
    public ResponseEntity<List<Pedido>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.findAll());
    }

    /*MÉTODO UPDATE*/

    @PostMapping("/pedido/update")
    public ResponseEntity<Pedido> update (@RequestBody Pedido pedido){
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedido.getId());
        if (pedidoOptional.isPresent()){
            Pedido myPedido = pedidoOptional.get();
            myPedido.setName(pedido.getName());

            return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.save(myPedido));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*MÉTODO DELETE*/

    @DeleteMapping("/pedido/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        pedidoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT). build();
    }

    /*ADD ITEM*/


    /*
    @PostMapping("/pedido/addItem")
    public ResponseEntity<?> addItem (@RequestParam Long pedidoId, @RequestParam Long itemId){
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        pedidoOptional.get().addItem(itemOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.save(pedidoOptional.get()));
    }
    */


    @PostMapping("/pedido/addItem")
    public ResponseEntity<?> addItem(@RequestParam Long pedidoId, @RequestParam Long itemId) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (!pedidoOptional.isPresent() || !itemOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pedido ou Item não encontrado.");
        }

        Pedido pedido = pedidoOptional.get();
        Item item = itemOptional.get();
        item.setPedido(pedido);
        itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }


    @PostMapping("/pedido/removeItem")
    public ResponseEntity<?> remove (@RequestBody Long pedidoId, Long itemId){
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        pedidoOptional.get().removeItem(itemOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.save(pedidoOptional.get()));
    }
}
