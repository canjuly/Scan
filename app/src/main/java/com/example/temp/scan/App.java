package com.example.temp.scan;

import java.util.List;

/**
 * Created by temp on 2019/5/16.
 */

public class App {


    /**
     * log_id : 7850770388979869104
     * direction : 0
     * words_result_num : 9
     * words_result : [{"words":"第一行代码"},{"words":" Android"},{"words":"第2版"},{"words":"郭霖◎著"},{"words":"基于 ANDroid7.0"},{"words":"难点、疑点透彻讲解"},{"words":"通俗易懂无废话"},{"words":"入门到上架AP"},{"words":"中国工信出版集团"}]
     */

    private long log_id;
    private int direction;
    private int words_result_num;
    private List<WordsResultBean> words_result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * words : 第一行代码
         */

        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }
}
