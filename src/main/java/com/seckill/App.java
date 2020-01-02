package com.seckill;

import com.seckill.dao.UserDOMapper;
import com.seckill.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.seckill"})
@RestController
@MapperScan("com.seckill.dao")
public class App 
{

    @Autowired
    private UserDOMapper userDOMapper;


    @RequestMapping("/")
    public String home(){
        UserDO user = userDOMapper.selectByPrimaryKey(1);
        if(user == null){
            return "cannot find user";
        }
        String name = user.getName();
        return name;
    }


    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    }
}
