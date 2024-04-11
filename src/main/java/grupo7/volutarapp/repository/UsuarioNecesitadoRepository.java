package grupo7.volutarapp.repository;

import grupo7.volutarapp.model.entity.UsuarioNecesitado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioNecesitadoRepository extends JpaRepository<UsuarioNecesitado, Long> {

}