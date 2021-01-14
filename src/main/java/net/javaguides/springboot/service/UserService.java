package net.javaguides.springboot.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.javaguides.springboot.entity.ActivityLogEntity;
import net.javaguides.springboot.entity.BlockUserEntity;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.enums.ActivityType;
import net.javaguides.springboot.repository.ActivityLogEntityRepo;
import net.javaguides.springboot.repository.BlockUserRepository;
import net.javaguides.springboot.repository.MessageRecorderRepository;
import net.javaguides.springboot.repository.UserRepository;

@Service
@Transactional
public class UserService implements UserServiceInterface{

	private final UserRepository userRepo;
	private final ActivityLogEntityRepo activityLogEntityRepo;
	private final BlockUserRepository blockedUserRepo;
	
	@Autowired
	public UserService(UserRepository userRepo, ActivityLogEntityRepo activityLogEntityRepo, BlockUserRepository blockedUserRepo) {
		this.userRepo = userRepo;
		this.activityLogEntityRepo = activityLogEntityRepo;
		this.blockedUserRepo = blockedUserRepo;
	}
	
	@Override
	public String save(User user) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassWord());
		ActivityLogEntity entity = new ActivityLogEntity();
		user.setPassWord(encodedPassword);
		try {
			userRepo.save(user);
		} catch (Exception e) {
			entity.setActivity(ActivityType.INVALID_REGISTER);
			entity.setUserName("---");
			activityLogEntityRepo.save(entity);
			throw new Exception("Invalid Register");
		}
		
		entity.setUserName(user.getUserName());
		entity.setActivity(ActivityType.REGISTER);
		activityLogEntityRepo.save(entity);
		return user.getUserName();
	}

	@Override
	public Boolean block(String angryName, String blockedName) {
		User angry = userRepo.findByUserName(angryName);
		User blocked = userRepo.findByUserName(blockedName);
		if(angry.getId() != null && blocked.getId() != null) {
			BlockUserEntity blockEntity = new BlockUserEntity();
			blockEntity.setAngryId(angry.getId());
			blockEntity.setBlockedId(blocked.getId());
			blockedUserRepo.save(blockEntity);
			return true;
		}	
		return false;
	}

	@Override
	public List<User> findAll() {
		List<User> listUsers = userRepo.findAll();
		return listUsers;
	}

	@Override
	public User findByEmail(String mail) {
		User user = userRepo.findByEmail(mail);
		return user;
	}

	@Override
	public User findByUserName(String userName) {
		User user = userRepo.findByUserName(userName);
		return user;
	}

	@Override
	public Set<String> findAllByName() {
		List<User> listUsers = userRepo.findAll();
		Set<String> response = new HashSet<String>();
		listUsers.forEach(user ->{
			String name = user.getUserName();
			response.add(name);
		});
		
		return response;
	}

	@Override
	public Boolean blockControl(String angryName, String blockedName) {
		User angry = userRepo.findByUserName(angryName);
		User blocked = userRepo.findByUserName(blockedName);
		List<BlockUserEntity> listOfBlock = blockedUserRepo.findAllByAngryId(angry.getId());
		ArrayList<Long> blockedIds = new ArrayList<Long>();
		int loop = listOfBlock.size();
		for(int flag = 0; flag<loop;flag++) {
			blockedIds.add(listOfBlock.get(flag).getBlockedId());
		}
		if(blockedIds.contains(blocked.getId())) {
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean unblock(String angryName, String blockedName) {
		User angry = userRepo.findByUserName(angryName);
		User blocked = userRepo.findByUserName(blockedName);
		
		if(angry.getId() != null && blocked.getId()!= null) {
			try {
				blockedUserRepo.unblock(angry.getId(), blocked.getId());
			} 
			catch(Exception e){
				return false;
			}
		}
		
		return true;
	}
	
}
