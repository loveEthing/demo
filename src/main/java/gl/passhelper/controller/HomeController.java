package gl.passhelper.controller;

import gl.passhelper.dao.passhelper.TestMapper;
import gl.passhelper.data.DO.TestDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.script.ScriptExecutor;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class HomeController {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DefaultRedisScript script;

    @GetMapping("/index")
    public TestDO index() {
        return testMapper.selectById(1);
    }

    @GetMapping("/getKey")
    public String getKey(@RequestParam("key")String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @GetMapping("/keys")
    public Set<String> keys() {
        return stringRedisTemplate.keys("*");
    }

    @GetMapping("/set")
    public String set() {
        stringRedisTemplate.executePipelined((RedisCallback<?>) connection -> {
            StringRedisConnection redisConnection = (StringRedisConnection) connection;
            redisConnection.set("test1", "test1value");
            redisConnection.set("test2", "test2value");
            redisConnection.incr("user:1:ratio");
            redisConnection.incrBy("user:2:ratio", 10);
            return null;
        });
        List<String> keys = new ArrayList<>();
        keys.add("mylist");
        keys.add("mylist");
        Object execute = stringRedisTemplate.execute(script, new StringRedisSerializer(),new StringRedisSerializer(), keys);
        if (execute == null) {
            System.out.println("null");
        } else {
            System.out.println("script result:" + execute);
        }
        return "OK";
    }
}
