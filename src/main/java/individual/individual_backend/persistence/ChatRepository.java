package individual.individual_backend.persistence;

import individual.individual_backend.persistence.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatEntity, Integer> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ChatEntity c " +
            "JOIN c.messages m " +
            "WHERE c.advertId.id = :advertId " +
            "AND ((m.sender.id = :senderId AND m.recipient.id = :recipientId) " +
            "OR (m.sender.id = :recipientId AND m.recipient.id = :senderId))")
    boolean doesChatExist(
            @Param("advertId") Integer advertId,
            @Param("senderId") Integer senderId,
            @Param("recipientId") Integer recipientId);

    @Query("SELECT c FROM ChatEntity c " +
            "JOIN c.messages m " +
            "WHERE c.advertId.id = :advertId " +
            "AND ((m.sender.id = :senderId AND m.recipient.id = :recipientId) " +
            "OR (m.sender.id = :recipientId AND m.recipient.id = :senderId)) " +
            "ORDER BY m.time ASC")
    Optional<ChatEntity> findChat(
            @Param("advertId") Integer advertId,
            @Param("senderId") Integer senderId,
            @Param("recipientId") Integer recipientId);


    @Query("SELECT DISTINCT c FROM ChatEntity c " +
            "JOIN FETCH c.messages m " +
            "WHERE m.sender.id = :userId OR m.recipient.id = :userId")
    List<ChatEntity> findChatsByUserId(@Param("userId") Integer userId);
}
