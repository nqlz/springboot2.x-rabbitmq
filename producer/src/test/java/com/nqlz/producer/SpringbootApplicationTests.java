package com.nqlz.producer;

import com.nqlz.producer.producer.RabbitSender;
import com.nqlz.repository.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

    @Autowired
    private RabbitSender rabbitSender;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testSender1() throws Exception {
        HashMap<Object, Object> properties = new HashMap<>();
        properties.put("number", "123456");
        properties.put("send time:", simpleDateFormat.format(new Date()));
        rabbitSender.send("Hello,你这该死的温柔", properties);
    }
    @Test
    public void testSender2() throws Exception {
        Order order=new Order("56461","张三李四");
        rabbitSender.sendOrder(order);
    }
}
