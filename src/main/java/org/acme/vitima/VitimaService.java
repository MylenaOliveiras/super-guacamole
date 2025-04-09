package org.acme.vitima;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.acme.crime.Crime;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class VitimaService {

    public List<VitimaDetailsDTO> listAll() {
        return Vitima.<Vitima>listAll().stream()
                .map(v -> new VitimaDetailsDTO(
                        v.id,
                        v.nomeCompleto,
                        v.genero,
                        v.idade,
                        v.crimes.stream().map(c -> c.id).collect(Collectors.toList())
                )).toList();
    }

    public VitimaDetailsDTO findById(Long id) {
        Vitima v = Vitima.findById(id);
        if (v == null) return null;

        return new VitimaDetailsDTO(
                v.id,
                v.nomeCompleto,
                v.genero,
                v.idade,
                v.crimes.stream().map(c -> c.id).collect(Collectors.toList())
        );
    }

    @Transactional
    public Response create(VitimaCreateDTO dto) {
        Vitima v = new Vitima();

        List<Crime> crimes = Crime.find("id in ?1", dto.crimes).list();


        if (crimes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Crime não encontrado")
                    .build();
        }

        v.nomeCompleto = dto.nomeCompleto;
        v.idade = dto.idade;
        v.genero = dto.genero;
        v.crimes = crimes;

        v.persist();

        return Response.ok().build();
    }

    @Transactional
    public Response atualizar(Long id, VitimaCreateDTO dto) {
        Vitima v = Vitima.findById(id);

        if (v == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Vitima não encontrada").build();
        }

        List<Crime> crimes = Crime.find("id in ?1", dto.crimes).list();

        if (crimes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Crime não encontrado")
                    .build();
        }

        v.nomeCompleto = dto.nomeCompleto;
        v.idade = dto.idade;
        v.crimes = crimes;

        v.persist();


        VitimaDetailsDTO vitima = new VitimaDetailsDTO(
                v.id,
                v.nomeCompleto,
                v.genero,
                v.idade,
                v.crimes.stream().map(c -> c.id).collect(Collectors.toList())
        );

        return Response.ok(vitima).build();
    }

    @Transactional
    public Response deletar(Long id) {
         Vitima.deleteById(id);
         return Response.status(Response.Status.ACCEPTED).entity("Vitima deletada").build();
    }
}
