package com.anhtq.app.admin.repository;

import com.anhtq.app.admin.doamin.RoleEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  String FIND_ROLE_BY_USER_NAME =
      "select tr.* from public.tbl_role as tr inner join public.tbl_user_role as tur on tr.role_id = tur.role_id where tur.user_id = :userId";

  Optional<RoleEntity> findByName(String name);

  List<RoleEntity> findByIdIn(List<Long> ids);

  @Query(value = FIND_ROLE_BY_USER_NAME, nativeQuery = true)
  List<RoleEntity> findByUserId(Long userId);
}
