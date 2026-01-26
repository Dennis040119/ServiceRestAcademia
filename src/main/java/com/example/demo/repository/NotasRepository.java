package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Notas;

public interface NotasRepository extends JpaRepository<Notas, String> {

}
