package com.hwgo.javagenericity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangbin
 * @Date 2019-07-22 10:51
 */
public class A<T> {
    public A(T t) {
        this.t = t;
    }

    private T t;

    public void setT(T t) {
        this.t = t;

    }

    public static class B {
        private A<? extends Object> mA;

        public B(A<? extends Object> a) {
            mA = a;
        }

        public void setNumbers(List<? extends Number> numbers) {

        }

        public void setNumbers2(List<? super Number> numbers) {

        }
    }

    public static void main(String[] args) {
        B b = new B(new A<String>(""));
        List<? super Number> charSequenceList = new ArrayList<>();
        charSequenceList.add(1);
        charSequenceList.add(1f);
        Object object = charSequenceList.get(0);
        b.setNumbers2(charSequenceList);

        List<? extends Number> numbers = new ArrayList<>();
        Number number = numbers.get(0);
        b.setNumbers(numbers);
    }

}
