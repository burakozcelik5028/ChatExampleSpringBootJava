package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.entity.MessageRecorderEntity;

@Repository
public interface MessageRecorderRepository extends JpaRepository<MessageRecorderEntity, Long>{
	List<MessageRecorderEntity> findAllByReceiverName(String receiverName);
}
