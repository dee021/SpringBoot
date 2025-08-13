package com.example.firstproject.service;

import com.example.firstproject.dto.PizzaDto;
import com.example.firstproject.entity.Pizza;
import com.example.firstproject.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PizzaService {
    @Autowired
    PizzaRepository pizzaRepository;

    public List<PizzaDto> index() {
        return pizzaRepository.findAll().stream()
                .map(pizza -> PizzaDto.createDto(pizza))
                .collect(Collectors.toList());
    }

    public PizzaDto show(Long id) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id가 없음"));
        return PizzaDto.createDto(pizza);
    }

    @Transactional
    public PizzaDto create(PizzaDto dto) {
        Pizza pizza = Pizza.createPizza(dto);
        return PizzaDto.createDto(pizzaRepository.save(pizza));
    }

    public PizzaDto update(Long id, PizzaDto dto) {
        Pizza target = pizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("피자 수정 실패. 해당 id가 없습니다."));

        target.patch(dto);
        Pizza updated = pizzaRepository.save(target);
        return PizzaDto.createDto(updated);
    }

    public PizzaDto delete(Long id) {
        Pizza target = pizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("피자 삭제 실패. 해당 id가 없습니다."));
        pizzaRepository.delete(target);
        return PizzaDto.createDto(target);
    }
}
