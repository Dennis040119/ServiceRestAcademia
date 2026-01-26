package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Grado;

public interface GradoRepository extends JpaRepository<Grado, String> {

}
