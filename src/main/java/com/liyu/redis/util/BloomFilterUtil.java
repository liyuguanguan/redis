package com.liyu.redis.util;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.PrimitiveSink;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 *n以及误判率fpp
 * 布隆过滤 https://www.cnblogs.com/z941030/p/9218356.html
 * @Author: liyu.guan
 * @Date: 2019/3/27 下午3:38
 */
public class BloomFilterUtil {


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 预计插入的元素总数
//        int expectedInsertions = Integer.MAX_VALUE>>4;
        int expectedInsertions = 50000000;
        // 期望误判率
        double fpp = 0.0000000001;

        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), expectedInsertions, fpp);
        bloomFilter.put("asd");
        bloomFilter.put("asd1");
        bloomFilter.put("asd2");
        //判断是否包含
        System.out.println(bloomFilter.mightContain("asd3"));
        System.out.println(System.currentTimeMillis()-start);


        BloomFilter<Email> emailBloomFilter = BloomFilter.create(new Funnel<Email>() {
            @Override
            public void funnel(Email from, PrimitiveSink into) {
                into.putString(from.getDomain(), Charsets.UTF_8);
            }
        }, expectedInsertions, fpp);


        System.out.println(new Email("xiaominb","haha").hashCode());
        System.out.println(new Email("xiaominb","haha").hashCode());


        emailBloomFilter.put(new Email("xiaominb","haha"));
        System.out.println(emailBloomFilter.mightContain(new Email("xiaominb", "haha")));;
    }
}

@Data
@ToString
@Builder
class Email{
    private String userName;
    private String domain;
}
