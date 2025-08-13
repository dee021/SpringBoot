package com.example.firstproject.dto;

import com.example.firstproject.entity.Pizza;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PizzaDto {
    private Long id;
    private String name;
    private String price;

    public static PizzaDto createDto(Pizza pizza) {
        return new PizzaDto(pizza.getId(), pizza.getName(), pizza.getPrice());
    }

    public Pizza toEntity(){
        return new Pizza(id, name, price);
    }
}
