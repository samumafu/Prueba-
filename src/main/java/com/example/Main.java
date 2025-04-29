package com.example;

import java.sql.Connection;

import com.example.model.Actor;
import com.example.repository.ActorRepository;
import com.example.repository.Repository;
import com.example.utils.DatabaseConnection;
public class Main {
    public static void main(String[] args) {
        try (Connection myConnection = DatabaseConnection.getInstance()) {
            Repository<Actor> repository = new ActorRepository();

            // Mostrar un actor existente
            Actor actor1 = repository.getByID(1);
            System.out.println("Actor con ID 1: " + actor1);

            // Insertar nuevo actor
            Actor nuevoActor = new Actor(null, "Juan", "Pérez");
            repository.save(nuevoActor);
            System.out.println("Actor insertado: " + nuevoActor);  // Imprimir detalles del actor insertado

            // Suponiendo que ahora el ID fue asignado automáticamente, puedes buscarlo por nombre (o ver en Workbench) y actualizarlo:
            // Simulamos que su ID es 202 por ejemplo:
            Actor actorActualizado = new Actor(202, "Juan", "Pérez Modificado");
            repository.save(actorActualizado);
            System.out.println("Actor actualizado: " + actorActualizado);  // Imprimir detalles del actor actualizado

            // Eliminar actor
            repository.delete(202);
            System.out.println("Actor eliminado con ID 202");  // Imprimir ID del actor eliminado

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
