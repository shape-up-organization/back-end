package br.com.shapeup.adapters.output.repository.jpa.common;

import br.com.shapeup.adapters.output.repository.model.common.AddressEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<AddressEntity, UUID> {
}
