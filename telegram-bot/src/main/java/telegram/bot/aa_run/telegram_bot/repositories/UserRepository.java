package telegram.bot.aa_run.telegram_bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import telegram.bot.aa_run.telegram_bot.models.User;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ModelBase, Long> {

    @Query(value = "SELECT * FROM java_spring_bot.users WHERE telegram_id = ?1", nativeQuery = true)
    public Optional<User> findByTelegramId(Long telegramId);

    @Query(value = "SELECT * FROM java_spring_bot.users WHERE name = ?1", nativeQuery = true)
    public Optional<User> findByUserName(String userName);

    @Query(value = "SELECT * FROM java_spring_bot.users WHERE user_status = ?1", nativeQuery = true)
    public Optional<User> findByUserStatus(String userStatus);
}
