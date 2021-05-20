package com.gmail.portnova.julia.web.controller.api;

import com.gmail.portnova.julia.service.ItemApiService;
import com.gmail.portnova.julia.service.ItemService;
import com.gmail.portnova.julia.service.model.ItemApiDTO;
import com.gmail.portnova.julia.service.model.ItemDTO;
import com.gmail.portnova.julia.web.validator.ItemValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemApiController {
    private final ItemService itemService;
    private final ItemApiService itemApiService;
    private final ItemValidator itemValidator;

    @GetMapping
    public List<ItemDTO> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ItemApiDTO getItemByUuid(@PathVariable("id") String uuidString) {
        return itemApiService.getItemApiByUuid(uuidString);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleByUuid(@PathVariable("id") String uuidString) {
        itemApiService.deleteItemApiByUuidNotRelatedToSaleUser(uuidString);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addItemWithPriceRelatedToSaleUser(@RequestBody ItemApiDTO item,
                                                                  BindingResult result) {
        itemValidator.validate(item, result);
        if (result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            itemApiService.addItemToDatabase(item);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

}
