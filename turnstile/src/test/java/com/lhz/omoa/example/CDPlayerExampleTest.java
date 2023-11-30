package com.lhz.omoa.example;

import com.lyt.ims.omoa.config.Config0Enums;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author laihz
 * @date 2023/11/28 15:49
 */
@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Config0Enums.class})
public class CDPlayerExampleTest {
    @Test
    public void test() {

    }
}
