package br.com.shapeup.adapters.output.repository.jpa.common;

import br.com.shapeup.adapters.output.repository.model.common.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressJpaRepository extends JpaRepository<AddressEntity, UUID> {
}
