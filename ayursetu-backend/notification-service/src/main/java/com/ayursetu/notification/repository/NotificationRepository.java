package com.ayursetu.notification.repository;

import com.ayursetu.notification.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByUserId(Long userId);
    
    List<Notification> findByStatus(Notification.NotificationStatus status);
    
    List<Notification> findByType(Notification.NotificationType type);
    
    List<Notification> findByChannel(Notification.NotificationChannel channel);
    
    List<Notification> findByUserIdAndStatus(Long userId, Notification.NotificationStatus status);
    
    List<Notification> findByUserIdAndType(Long userId, Notification.NotificationType type);
    
    Optional<Notification> findByUserIdAndTitleAndCreatedAtBetween(
            Long userId, String title, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT n FROM Notification n WHERE n.createdAt BETWEEN :startDate AND :endDate")
    List<Notification> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT n FROM Notification n WHERE n.userId = :userId AND n.createdAt BETWEEN :startDate AND :endDate")
    List<Notification> findByUserIdAndDateRange(@Param("userId") Long userId, 
                                               @Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.status = :status")
    Long countByStatus(@Param("status") Notification.NotificationStatus status);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.type = :type")
    Long countByType(@Param("type") Notification.NotificationType type);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.channel = :channel")
    Long countByChannel(@Param("channel") Notification.NotificationChannel channel);
    
    @Query("SELECT n.type, COUNT(n) as count FROM Notification n GROUP BY n.type")
    List<Object[]> getNotificationTypeStats();
    
    @Query("SELECT n.status, COUNT(n) as count FROM Notification n GROUP BY n.status")
    List<Object[]> getNotificationStatusStats();
    
    @Query("SELECT n.channel, COUNT(n) as count FROM Notification n GROUP BY n.channel")
    List<Object[]> getNotificationChannelStats();
    
    @Query("SELECT n FROM Notification n WHERE n.status = 'PENDING' ORDER BY n.createdAt ASC")
    List<Notification> findPendingNotifications();
    
    @Query("SELECT n FROM Notification n WHERE n.status = 'FAILED' AND n.createdAt >= :retryAfter")
    List<Notification> findFailedNotificationsForRetry(@Param("retryAfter") LocalDateTime retryAfter);
}


