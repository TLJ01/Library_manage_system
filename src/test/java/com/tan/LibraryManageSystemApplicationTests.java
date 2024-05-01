package com.tan;

import com.tan.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LibraryManageSystemApplicationTests {

    @Test
    void contextLoads() {
        String md5String1 = Md5Util.getMD5String("123456");
        System.out.println(md5String1);

        String md5String2 = Md5Util.getMD5String("123456");
        System.out.println(md5String2);

        String md5String3 = Md5Util.getMD5String("123456");
        System.out.println(md5String3);
    }

}
