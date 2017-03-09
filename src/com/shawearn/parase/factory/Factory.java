package com.shawearn.parase.factory;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 工厂类；
 * <p>
 * Created by Shawearn on 3/8/2017.
 */
public class Factory {

    private static Factory factory;

    // 字典文件路径；
    private static final String filePath = "diction10k.txt";

    // 字典集合；
    private static Set<String> dictionary = null;

    // 字典中最大单词长度；
    private static int maxLength = -1;

    public static Factory getInstance() {
        if (factory == null) {
            factory = new Factory();
        }
        return factory;
    }

    private Factory() {
    }

    /**
     * 获取字典文件中的单词集合；
     *
     * @return
     * @throws IOException
     */
    public Set<String> getDictionary() throws IOException {
        if (dictionary == null) {
            dictionary = this.readWordsToSet(filePath);
        }
        return dictionary;
    }

    /**
     * 获取字典中最大单词长度
     *
     * @return
     * @throws IOException
     */
    public int getMaxLength() throws IOException {
        if (maxLength < 0) {
            maxLength = getMaxWordLengthFromFile(filePath);
        }
        return maxLength;
    }

    /**
     * 读取文本文件并将文件中的单词保存到 Set 中；
     *
     * @param filePath 文件路径；
     * @return
     */
    private Set<String> readWordsToSet(String filePath) throws IOException {
        Set<String> set = new HashSet<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8"));
        String lineTxt = null;
        while ((lineTxt = br.readLine()) != null) {
            lineTxt = lineTxt.trim();
            set.add(lineTxt.toLowerCase());
        }
        br.close();
        return set;
    }

    /**
     * 获取文本文件中最长的单词长度；
     *
     * @param filePath 文件路径；
     * @return
     * @throws IOException
     */
    private int getMaxWordLengthFromFile(String filePath) throws IOException {
        int maxLength = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8"));
        String lineTxt = null;
        while ((lineTxt = br.readLine()) != null) {
            lineTxt = lineTxt.trim();
            maxLength = (lineTxt.length() > maxLength) ? lineTxt.length() : maxLength;
        }
        br.close();
        return maxLength;
    }

}
