package com.example.springjdbctemplatesecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    JdbcTemplate template;

    private Logger logger= LoggerFactory.getLogger(UserService.class);

    public String insertOne(HaiUsers haiUser){
        String info=haiUser.getUsername()+" has inserted";
        template.update("insert into hai_users values(hai_users_seq.nextval,?,?,?,?)",new Object[]{haiUser.getUsername(),haiUser.getPassword(),haiUser.getRole(),haiUser.getStatus()});
        return info;
    }

    public HaiUsers findByUsername(String username){
        HaiUsers hai=template.queryForObject("select * from hai_users where username=?",new Object[]{username},new BeanPropertyRowMapper<>(HaiUsers.class));
        logger.info(hai.toString());
        return hai;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HaiUsers haiUsers=findByUsername(username);
        if(haiUsers==null)
            throw new UsernameNotFoundException("Invalid username");
        return haiUsers;
    }
}
