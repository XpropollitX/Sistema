package com.example.Sistema_Biblioteca.repository;

import com.example.Sistema_Biblioteca.entity.DetalleAlquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleAlquilerRepository extends JpaRepository <DetalleAlquiler,Long> {

}
