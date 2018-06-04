package dbdemo.redis.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dbdemo.mysql.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class UserRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void add(String key, Long time, User user) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(user), time, TimeUnit.MINUTES);
    }

    public void add(String key, Long time, List<User> users) {
        redisTemplate.opsForValue().set(key, new Gson().toJson(users), time, TimeUnit.MINUTES);
    }

    public User get(String key) {
        String userJson = redisTemplate.opsForValue().get(key);
        User user = null;
        if (!StringUtils.isEmpty(userJson)) {
            user = new Gson().fromJson(userJson, User.class);
        }
        return user;
    }

    public List<User> getList(String key) {
        String usersJson = redisTemplate.opsForValue().get(key);
        List<User> users = null;
        if (!StringUtils.isEmpty(usersJson)) {
            users = new Gson().fromJson(usersJson, new TypeToken<List<User>>() {}.getType());
        }
        return users;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);    //实际还是调用了redisTemplate.delete(key),为什么不直接这么写？
    }
}
