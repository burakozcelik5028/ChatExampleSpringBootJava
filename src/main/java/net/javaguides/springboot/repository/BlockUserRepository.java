package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.javaguides.springboot.entity.BlockUserEntity;

@Repository
public interface BlockUserRepository extends JpaRepository<BlockUserEntity,Long>{
	List<BlockUserEntity> findAllByAngryId(Long angryId);
	
	@Transactional
	@Modifying
	@Query("delete from BlockUserEntity b where b.angryId = :angryId and b.blockedId = :blockedId")
	void unblock(Long angryId, Long blockedId);
	
}
