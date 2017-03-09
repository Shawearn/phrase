package com.shawearn.parase;

import com.shawearn.parase.factory.Factory;

import java.io.IOException;
import java.util.*;

/**
 * 工程主入口；
 * <p/>
 * Created by Shawearn on 3/8/2017.
 */
public class Main {

    // 字典集合；
    private static Set<String> dict;

    // 工厂实例；
    private static Factory factory;

    // 分词结果；
    private List<String> resultList;

    // 用于存放控制台输入的分词语句；
    private List<String> untreatedList = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        factory = Factory.getInstance();
        dict = factory.getDictionary();

        // 输入；
        main.input();

        // 开始；
        main.start();
    }

    private void start() throws IOException {
        if (untreatedList != null && untreatedList.size() != 0) {
            for (int index = 0; index < untreatedList.size(); index++) {
                resultList = new ArrayList<String>();
                String untreated = untreatedList.get(index);
                // 进行正则匹配，如果出现英文字母之外的字符，则不进行分词操作；
                String regex = "^[a-zA-Z]+$";
                boolean isMatches = untreated.matches(regex);
                if (!isMatches) {
                    System.out.println("待分词的字符串中不能出现英文字母之外的字符！");
                    return;
                }
                // 进行分词操作；
                this.split("", untreated);
                System.out.println(untreated + " 分词结果：");
                if (resultList.size() > 0) {
                    for (String result : resultList) {
                        System.out.println(result);
                    }
                } else {
                    System.out.println("无分词结果！");
                }
            }
        }
    }

    private void input() {
        System.out.println("请输入分词句子数量：");
        Scanner numberScaner = new Scanner(System.in);
        int num = 0;
        try {
            num = numberScaner.nextInt();
            if (num < 1) {
                System.out.println("输入条数不能小于 1");
                this.input();
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("只能输入数字");
            this.input();
            return;
        }
        untreatedList = new ArrayList<String>();
        for (int index = 0; index < num; index++) {
            System.out.println("输入第 " + (index + 1) + "条要进行分词操作的语句：");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.next().trim();
            untreatedList.add(str);
        }
    }

    /**
     * 分词操作；
     *
     * @param treated   已进行分词操作的字符串；
     * @param untreated 未进行分词操作的字符串；
     * @throws IOException
     */
    private void split(String treated, String untreated) throws IOException {
        // isSuccess 用于标志是否分词成功，true 表示分词成功，false 表示分词失败；
        boolean isSuccess = false;
        int maxLength = factory.getMaxLength();
        if (untreated == null || "".equals(untreated.trim())) {
            return;
        }
        for (int end = 1; end <= untreated.length(); end++) {
            // 若 end 的值大于字典中最大单词的长度，则没必要继续做分词操作，因为字典中已经没有单词能与之匹配；
            if (end > maxLength) {
                return;
            }

            // 截取字符串；
            String world = untreated.substring(0, end);

            // 如果当前截取的字符是存在于字典中的单词；
            if (this.isWorld(world)) {
                // 找到单词，将 isSuccess 标志为 true；
                isSuccess = true;
                // 拼接新的已进行分词操作的字符串；
                String newTreated = ("".equals(treated)) ? world : treated + " " + world;
                // 如果当前已经截取 untreated 的最后一个字符，则表示分词成功；
                if (end == untreated.length()) {
                    // 将分词结果存放到 resultList 中；
                    resultList.add(newTreated);
                    return;
                }
                // 新的未进行分词操作的字符串；
                String newUntreated = untreated.substring(world.length());
                // 递归调用；
                this.split(newTreated, newUntreated);
            }
        }
        // 分词失败；
        if (!isSuccess) {
            return;
        }
    }

    /**
     * 判断 world 是否是一个单词；
     *
     * @param world
     * @return
     */
    private boolean isWorld(String world) {
        return dict != null && dict.size() != 0 && dict.contains(world.toLowerCase());
    }
}
