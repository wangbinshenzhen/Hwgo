package com.hwgo.firstmodule;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author wangbin
 * @Date 2019-07-05 11:18
 */
public class Demo2Test {
     private Demo1 mDemo1;
    @Before
    public void setUp() throws Exception {
        mDemo1=new Demo1();

    }

    @Test
    public void rxConcat2() {
         mDemo1.rxConcat2();
    }
}