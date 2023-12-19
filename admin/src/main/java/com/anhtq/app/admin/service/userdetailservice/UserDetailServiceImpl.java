package com.anhtq.app.admin.service.userdetailservice;

import com.anhtq.app.admin.doamin.RoleEntity;
import com.anhtq.app.admin.doamin.UserEntity;
import com.anhtq.app.admin.excetion.ApiException;
import com.anhtq.app.admin.repository.RoleRepository;
import com.anhtq.app.admin.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user =
        userRepository
            .findByEmail(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User name " + username + " không tìm thấy."));

    List<RoleEntity> roleEntities = roleRepository.findByUserId(user.getId());

    if (CollectionUtils.isEmpty(roleEntities)) {
      throw new ApiException("User " + username + " không đủ quyền.", HttpStatus.FORBIDDEN);
    }

    List<SimpleGrantedAuthority> authorities =
        roleEntities.stream().map(e -> new SimpleGrantedAuthority(e.getName())).toList();

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .authorities(authorities)
        .password(user.getPassword())
        .accountLocked(Objects.equals(user.getStatus(), "03"))
        .build();
  }
}
