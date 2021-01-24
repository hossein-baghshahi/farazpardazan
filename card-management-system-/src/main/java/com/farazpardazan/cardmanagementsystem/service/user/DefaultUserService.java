package com.farazpardazan.cardmanagementsystem.service.user;

import com.farazpardazan.cardmanagementsystem.domain.User;
import com.farazpardazan.cardmanagementsystem.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Hossein Baghshahi
 */
@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(currentUsername).orElseThrow(UserNotFoundException::new);

    }
}
