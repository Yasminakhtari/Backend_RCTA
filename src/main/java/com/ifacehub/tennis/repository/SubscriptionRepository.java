package com.ifacehub.tennis.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ifacehub.tennis.entity.Subscription;
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
