package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.NotificationUsers;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationUsersRepository extends JpaRepository<NotificationUsers,Long> {
    @Query("SELECT nu FROM NotificationUsers nu WHERE nu.userId = :userId")
    List<NotificationUsers> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE NotificationUsers nu SET nu.status = 'read' WHERE nu.notification.id = :notificationId AND nu.userId = :userId")
    void markNotificationAsRead(Long notificationId, Long userId);
}
