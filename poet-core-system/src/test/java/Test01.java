import com.adventureboy.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test01 {
    @Autowired
    RedisUtil redisUtil;
    @Test
    public void test01() {
        String ymex = redisUtil.get("ymex");
        System.out.println(ymex);
    }
}
