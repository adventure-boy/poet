import cn.hutool.core.util.RandomUtil;
import com.adventureboy.system.utils.RandomStringUtil;

import java.util.Random;

/**
 * a-97 z-122 A-65 Z-90
 *
 */
public class Test01 {
    public static void main(String[] args) {
//        Math.random()
        String s = RandomUtil.randomString(4);
        System.out.println(s);
    }
}
