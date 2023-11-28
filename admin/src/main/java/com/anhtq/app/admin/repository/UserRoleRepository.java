package com.anhtq.app.admin.repository;

import com.anhtq.app.admin.doamin.UserRoleEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

  List<UserRoleEntity> findByUserId(Long userId);

  Optional<UserRoleEntity> findByUserIdAndRoleId(Long userId, Long roleId);
}
